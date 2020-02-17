package journal.gratitude.com.gratitudejournal.ui

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import journal.gratitude.com.gratitudejournal.R
import journal.gratitude.com.gratitudejournal.di.DaggerTestApplicationRule
import journal.gratitude.com.gratitudejournal.model.Entry
import journal.gratitude.com.gratitudejournal.repository.EntryRepository
import journal.gratitude.com.gratitudejournal.ui.entry.EntryFragment
import journal.gratitude.com.gratitudejournal.util.getText
import journal.gratitude.com.gratitudejournal.util.isEditTextValueEqualTo
import journal.gratitude.com.gratitudejournal.util.saveEntryBlocking
import journal.gratitude.com.gratitudejournal.util.waitFor
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate


@RunWith(AndroidJUnit4::class)
class EntryFragmentInstrumentedTest {

    private lateinit var repository: EntryRepository

    @get:Rule
    val rule = DaggerTestApplicationRule()

    @Before
    fun setupDaggerComponent() {
        repository = rule.component.entryRepository
    }

    @Test
    fun writtenEntry_showsShareButton() {
        val date = LocalDate.of(2019, 3, 22)
        val mockEntry = Entry(date, "test content")
        repository.saveEntryBlocking(mockEntry)

        val fragmentArgs = Bundle().apply {
            putString(EntryFragment.DATE, date.toString())
        }
        launchFragmentInContainer<EntryFragment>(
            themeResId = R.style.AppTheme,
            fragmentArgs = fragmentArgs
        )

        onView(withId(R.id.share_button))
            .check(matches(isDisplayed()))
        onView(withId(R.id.prompt_button))
            .check(matches(not(isDisplayed())))

    }

    @Test
    fun noEntry_showsPromptButton() {
        launchFragmentInContainer<EntryFragment>(
            themeResId = R.style.AppTheme
        )

        onView(withId(R.id.share_button)).check(matches(not(isDisplayed())))
        onView(withId(R.id.prompt_button)).check(matches(isDisplayed()))
    }

    @Test
    fun promptButton_changesHintText() {
        launchFragmentInContainer<EntryFragment>(
            themeResId = R.style.AppTheme
        )

        onView(withId(R.id.entry_text)).check(matches(withHint("What are you grateful for?")))
        onView(withId(R.id.prompt_button)).perform(click())
        onView(withId(R.id.entry_text)).check(matches(not(withHint("What are you grateful for?"))))
    }

    @Test
    fun shareButton_opensShareActivity() {
        Intents.init()
        val date = LocalDate.of(2019, 3, 22)
        val mockEntry = Entry(date, "test content")
        repository.saveEntryBlocking(mockEntry)

        val fragmentArgs = Bundle().apply {
            putString(EntryFragment.DATE, date.toString())
        }
        launchFragmentInContainer<EntryFragment>(
            themeResId = R.style.AppTheme,
            fragmentArgs = fragmentArgs
        )

        val intent = Intent()
        val intentResult = Instrumentation.ActivityResult(Activity.RESULT_OK, intent)
        intending(anyIntent()).respondWith(intentResult)

        onView(withId(R.id.share_button)).perform(click())

        intended(
            allOf(
                hasAction(Intent.ACTION_CHOOSER),
                hasExtra(Intent.EXTRA_TITLE, "Share your gratitude progress")
            )
        )

        Intents.release()
    }

    @Test
    fun saveButton_navigatesBack() {
        val mockNavController = mock<NavController>()
        val scenario = launchFragmentInContainer<EntryFragment>(
            themeResId = R.style.AppTheme
        )
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        onView(withId(R.id.entry_text)).perform(
            typeText("Test string!"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.save_button)).perform(click())

        verify(mockNavController).navigateUp()
    }

    @Test
    fun saveButton_onMilestone_showsMilestoneDialog() {
        val mockNavController = mock<NavController>()
        val fragmentArgs = Bundle().apply {
            putInt(EntryFragment.NUM_ENTRIES, 4)
            putBoolean(EntryFragment.IS_NEW_ENTRY, true)
        }
        val scenario = launchFragmentInContainer<EntryFragment>(
            fragmentArgs = fragmentArgs,
            themeResId = R.style.AppTheme
        )
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        onView(withId(R.id.entry_text)).perform(
            typeText("Test string!"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.save_button)).perform(click())

        onView(withText("Share your achievement")).inRoot(isDialog()).check(matches(isDisplayed()))
    }

    @Test
    fun newInstance_makesFragment() {
        val date = LocalDate.now()
        val fragment = EntryFragment.newInstance(date)

        val actualDate = fragment.arguments?.get(EntryFragment.DATE)

        assertEquals(date.toString(), actualDate)
    }

    @Test
    fun entryFragment_longPressQuote_copiesToClipboard() {
        launchFragmentInContainer<EntryFragment>(
            themeResId = R.style.AppTheme
        )

        val quote =  getText(withId(R.id.inspiration))

        onView(withId(R.id.inspiration)).perform(longClick())
        onView(withId(R.id.entry_text)).perform(click())
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressKeyCode(KeyEvent.KEYCODE_V, KeyEvent.META_CTRL_MASK)


        onView(withId(R.id.entry_text)).check(matches(isEditTextValueEqualTo(quote)))
    }

    @Test
    fun entryFragment_longPressQuote_showsToast() {
        val scenario = launchFragmentInContainer<EntryFragment>(
            themeResId = R.style.AppTheme
        )

        var activity: Activity? = null
        scenario.onFragment { fragment ->
            activity = fragment.activity
        }

        onView(withId(R.id.inspiration)).perform(longClick())

        onView(withText(R.string.copied)).inRoot(withDecorView(not((activity?.window?.decorView))))
            .check(matches(isDisplayed()))
    }

    //these tests are all together to save testing debounce time
    @Test
    fun entryFragment_makeEdit_navigatesBack() {
        val mockNavController = mock<NavController>()
        val scenario = launchFragmentInContainer<EntryFragment>(
            themeResId = R.style.AppTheme
        )
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        //Simulate user typing
        onView(withId(R.id.entry_text)).perform(
            typeText("Test string!")
        )
        onView(isRoot()).perform(waitFor(550))
        onView(withId(R.id.entry_text)).perform(
            typeText("Yeehaw!"),
            closeSoftKeyboard()
        )

        //wait for debounce to detect changes
        onView(isRoot()).perform(waitFor(550))

        //back is pressed
        val mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        mDevice.pressBack()

        //dialog is displayed
        onView(withText(R.string.are_you_sure)).check(matches(isDisplayed()))

        //cancel is pressed
        onView(withId(android.R.id.button2)).perform(click())
        onView(withText(R.string.are_you_sure)).check(ViewAssertions.doesNotExist())

        //back pressed again
        mDevice.pressBack()

        //continue clicked
        onView(withId(android.R.id.button1)).perform(click())

        //back navigate
        verify(mockNavController).navigateUp()
    }

    @Test
    fun entryFragment_noEdit_navigatesBack_noDialog() {
        val mockNavController = mock<NavController>()
        val scenario = launchFragmentInContainer<EntryFragment>(
            themeResId = R.style.AppTheme
        )
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        val mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        mDevice.pressBack()

        //back navigate
        verify(mockNavController).navigateUp()

        onView(withText(R.string.are_you_sure)).check(ViewAssertions.doesNotExist())
    }

}