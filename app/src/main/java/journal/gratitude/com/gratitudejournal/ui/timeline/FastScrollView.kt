package journal.gratitude.com.gratitudejournal.ui.timeline

import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import journal.gratitude.com.gratitudejournal.R
import kotlin.math.min

class FastScrollView: LinearLayout {

    private val adapterDataObserver: RecyclerView.AdapterDataObserver = createAdapterDataObserver()

    private var isUpdateItemIndicatorsPosted = false


    //these all get provided by the client
    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerView.Adapter<*>? = null
        set(value) {
            field?.unregisterAdapterDataObserver(adapterDataObserver)
            field = value
            value?.let { newAdapter ->
                newAdapter.registerAdapterDataObserver(adapterDataObserver)
                postUpdateItemIndicators()
            }
        }
    private lateinit var getItemIndicatorText: (Int) -> String?

    //this can be used if we want to show an additional view like FastScrollerThumbView when we're touching over an item
    internal var onItemIndicatorTouched: ((Boolean) -> Unit)? = null

    //the actual items that will be in the scrubber view
        //string is what the item will show and the int is the position
    private val scrubberItemsData: MutableList<Pair<String, Int>> = ArrayList()
    val scrubberItems: List<String>
        get() = scrubberItemsData.map { it.first }


    private val isSetup: Boolean get() = (recyclerView != null)
    private var lastSelectedPosition: Int? = null


    fun setRecyclerView(
        recyclerView: RecyclerView,
        getItemIndicatorText: (Int) -> String
    ) {
        check(!isSetup) { "Only set this view's RecyclerView once!" }

        //set the recyclerview
        this.recyclerView = recyclerView
        //set the mapper from position to data
        this.getItemIndicatorText = getItemIndicatorText

        //set the adapter
        this.adapter = recyclerView.adapter.also {
            if (it != null) {
                updateItemIndicators()
            }
        }

        //todo what is this used for?
        recyclerView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            // RecyclerView#setAdapter calls requestLayout, so this can detect adapter changes
            if (recyclerView.adapter !== adapter) {
                adapter = recyclerView.adapter
            }
        }
    }

    //posts an update
    private fun postUpdateItemIndicators() {
        if (!isUpdateItemIndicatorsPosted) {
            isUpdateItemIndicatorsPosted = true
            post {
                if (recyclerView!!.run { isAttachedToWindow && adapter != null }) {
                    updateItemIndicators()
                }
                isUpdateItemIndicatorsPosted = false
            }
        }
    }

    //updates the items
    private fun updateItemIndicators() {
        //clear current items
        scrubberItemsData.clear()

        //recreate the scrubber items list and put it into scrubberItemsData
        createScrubberItemList(recyclerView!!, getItemIndicatorText)
            .toCollection(scrubberItemsData)

        //bind views
        bindItemViews()
    }


    //uses the mapper that the client provided and creates a list from it removing duplicates
    //analogous to ItemIndicatorsBuilder
        //todo maybe instead of removing dupes for ours it creates like headers and then items
        //where headers are the dates and items will be used to represent how much space goes between headers
    private fun createScrubberItemList(recyclerView: RecyclerView, getItemForPosition: (Int) -> String?): List<Pair<String, Int>> {
        return (0 until recyclerView.adapter!!.itemCount).mapNotNull { position ->
            val itemText = getItemForPosition(position)
            itemText?.let { itemText to position }
        }.distinctBy { it.first } //use the text to exclude any duplicates of this item category
        //todo the other impl has extra filtering that i'm not sure what for
    }

    private fun bindItemViews() {
        //reset the linear layout
        removeAllViews()

        if (scrubberItemsData.isEmpty()) return

        val views = ArrayList<View>() //todo since we aren't combining icons with text maybe we dont need a list of views?

        //the reddit version has both icons and text and it batches all the text items next to each other in a row, binds the icon, and then continues
        //since this is only working with text we can just put all the text into one textview
        views.add(createTextViewFromList(scrubberItems))

        //add each view to the linearlayout
        views.forEach(::addView)
    }

    //combines a list of strings to make one big text view
    private fun createTextViewFromList(scrubberItems: List<String>): TextView {
        return (LayoutInflater.from(context).inflate(
            R.layout.scrubber_item, this, false
        ) as TextView).apply {
            //todo set the attributes of the text view
            text = scrubberItems.joinToString(separator = "\n") { it }
            tag = scrubberItems //using the tag so that we can later look through the items in this textview
        }
    }

    //handles the scrolling when this view is touched
    override fun onTouchEvent(event: MotionEvent): Boolean {

        //if user lifts their finger off the view
        if (event.actionMasked in intArrayOf(MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL)) {
            isPressed = false
            clearSelectedItem()
            onItemIndicatorTouched?.invoke(false)
            return false
        }

        var consumed = false
        val touchY = event.y.toInt()
        // walks the children
            // in our case we will only have one child that is a text view
            // but in the reddit view we will have textviews and icons
        children.forEach { view ->
            //find the view in the children that has the touch happening to it
            if (view.containsY(touchY)) {
                @Suppress("UNCHECKED_CAST")
                val textInCurrentChild = view.tag as List<String> //get the children for this section of text

                val textIndicatorsTouchY = touchY - view.top
                val textLineHeight = view.height / textInCurrentChild.size //the height of each row of text
                val touchedIndicatorIndex = min(
                    textIndicatorsTouchY / textLineHeight,
                    textInCurrentChild.lastIndex
                )
                val touchedIndicator = textInCurrentChild[touchedIndicatorIndex]

                val centerY = view.y.toInt() + (textLineHeight / 2) + (touchedIndicatorIndex * textLineHeight)
                selectItemIndicator(touchedIndicator, centerY, view, textLine = touchedIndicatorIndex)

                consumed = true
            }
        }
        isPressed = consumed
        onItemIndicatorTouched?.invoke(consumed) //so the companion view can be shown
        return consumed
    }

    // scrolls the list!
    // makes haptic feedback
    // highlights the currently touched item
    private fun selectItemIndicator(touchedItem: String, centerY: Int, view: View, textLine: Int) {
        //look up in our list the touched text and find its position
        val positionOfTouchedItem = scrubberItemsData.first { it.first == touchedItem }.let { it.second }
        if (positionOfTouchedItem != lastSelectedPosition) {
            clearSelectedItem() //this item is no longer the one being touched
            lastSelectedPosition = positionOfTouchedItem
            scrollToPosition(positionOfTouchedItem)
        }

        performHapticFeedback(
            // Semantically, dragging across the indicators is similar to moving a text handle
            if (Build.VERSION.SDK_INT >= 27) {
                HapticFeedbackConstants.TEXT_HANDLE_MOVE
            } else {
                HapticFeedbackConstants.KEYBOARD_TAP
            }
        )

        //todo style the text
    }

    // resets so there is no currently selected item in the view
        // clears the styling for every item
    private fun clearSelectedItem() {
        lastSelectedPosition = null
        children.filterIsInstance<TextView>().forEach {
            //todo clear styling if we choose to add this for selected item
        }
    }


    private fun scrollToPosition(position: Int) {
        recyclerView!!.apply {
            stopScroll()
            smoothScrollToPosition(position)
        }
    }


    //listens to changes in the adapter and triggers the items indicators to be updated
    private fun createAdapterDataObserver(): RecyclerView.AdapterDataObserver {
        return object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                postUpdateItemIndicators()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) =
                onChanged()

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) =
                onChanged()

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) =
                onChanged()

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) =
                onChanged()
        }
    }


}

fun View.containsY(y: Int) = y in (top until bottom)