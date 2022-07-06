package journal.gratitude.com.gratitudejournal

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.presently.logging.AnalyticsLogger
import com.presently.settings.PresentlySettings
import dagger.hilt.android.AndroidEntryPoint
import journal.gratitude.com.gratitudejournal.model.CAME_FROM_NOTIFICATION
import journal.gratitude.com.gratitudejournal.ui.security.AppLockFragment
import journal.gratitude.com.gratitudejournal.util.reminders.NotificationScheduler
import journal.gratitude.com.gratitudejournal.util.reminders.ReminderReceiver.Companion.fromNotification
import javax.inject.Inject

@AndroidEntryPoint
class ContainerActivity : AppCompatActivity() {

    companion object {
        const val CHANNEL_ID = "Presently Gratitude Reminder"
        const val BACKUP_STATUS_CHANNEL = "Presently Automatic Backup Status"
        const val NOTIFICATION_SCREEN_EXTRA = "NOTIFICATION_EXTRA"
    }

    @Inject lateinit var settings: PresentlySettings
    @Inject lateinit var analyticsLogger: AnalyticsLogger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentTheme = settings.getCurrentTheme()
        setAppTheme(currentTheme)
        setContentView(R.layout.container_activity)

        createNotificationChannels()

        intent.extras?.let {
            val cameFromNotification = it.getBoolean(fromNotification, false)
            if (cameFromNotification) {
                analyticsLogger.recordEvent(CAME_FROM_NOTIFICATION)
            }
        }

        NotificationScheduler().configureNotifications(this, settings)

        if (resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            //lays app behind system bars
                //not in landscape mode so navigation bar doesn't block UI
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    override fun onResume() {
        super.onResume()
        isGooglePlayServicesAvailable(this)
    }

    private fun isGooglePlayServicesAvailable(activity: Activity): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(activity)
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show()
            }
            return false
        }
        return true
    }

    override fun onStart() {
        super.onStart()

        val isBiometricsEnabled = settings.isBiometricsEnabled()
        if (isBiometricsEnabled) {
            if (settings.shouldLockApp()) {
                val fragment = AppLockFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_fragment, fragment)
                    .commit()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (settings.isBiometricsEnabled()) {
            settings.setOnPauseTime()
        }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, getString(R.string.channel_name),  NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = getString(R.string.channel_description)
            notificationChannel.enableVibration(true)

            val backupChannel = NotificationChannel(BACKUP_STATUS_CHANNEL, getString(R.string.backup_channel_name), NotificationManager.IMPORTANCE_HIGH)
            backupChannel.description = getString(R.string.backup_channel_description)
            backupChannel.enableVibration(true)

            val notificationManager = getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannels(listOf(notificationChannel, backupChannel))

        }
    }

    private fun setAppTheme(currentTheme: String) {
        when (currentTheme) {
            "Sunset" -> setTheme(R.style.AppTheme_SUNSET)
            "Moonlight" -> setTheme(R.style.AppTheme_MOONLIGHT)
            "Midnight" -> setTheme(R.style.AppTheme_MIDNIGHT)
            "Ivy" -> setTheme(R.style.AppTheme_IVY)
            "Dawn" -> setTheme(R.style.AppTheme_DAWN)
            "Wesley" -> setTheme(R.style.AppTheme_WESLEY)
            "Moss" -> setTheme(R.style.AppTheme_MOSS)
            "Clean" -> setTheme(R.style.AppTheme_CLEAN)
            "Glacier" -> setTheme(R.style.AppTheme_GLACIER)
            "Gelato" -> setTheme(R.style.AppTheme_GELATO)
            "Waves" -> setTheme(R.style.AppTheme_WAVES)
            "Beach" -> setTheme(R.style.AppTheme_BEACH)
            "Field" -> setTheme(R.style.AppTheme_FIELD)
            "Western" -> setTheme(R.style.AppTheme_WESTERN)
            "Sunlight" -> setTheme(R.style.AppTheme_SUNLIGHT)
            "Tulip" -> setTheme(R.style.AppTheme_TULIP)
            "Rosie" -> setTheme(R.style.AppTheme_ROSIE)
            "Daisy" -> setTheme(R.style.AppTheme_DAISY)
            "Matisse" -> setTheme(R.style.AppTheme_MATISSE)
            "Clouds" -> setTheme(R.style.AppTheme_CLOUDS)
            "Monstera" -> setTheme(R.style.AppTheme_MONSTERA)
            "Lotus" -> setTheme(R.style.AppTheme_LOTUS)
            "Katie" -> setTheme(R.style.AppTheme_KATIE)
            "Brittany" -> setTheme(R.style.AppTheme_BRITTANY)
            "Jungle" -> setTheme(R.style.AppTheme_JUNGLE)
            "Julie" -> setTheme(R.style.AppTheme_JULIE)
            "Ellen" -> setTheme(R.style.AppTheme_ELLEN)
            "Danah" -> setTheme(R.style.AppTheme_DANAH)
            "Ahalya" -> setTheme(R.style.AppTheme_AHALYA)
            "Rem'mie" -> setTheme(R.style.AppTheme_REMMIE)
            "Marsha" -> setTheme(R.style.AppTheme_MARSHA)
            "Brayla" -> setTheme(R.style.AppTheme_BRAYLA)
            "Autumn" -> setTheme(R.style.AppTheme_AUTUMN)
            "Betty" -> setTheme(R.style.AppTheme_BETTY)
            "Boo" -> setTheme(R.style.AppTheme_BOO)
            "Calm" -> setTheme(R.style.AppTheme_CALM)
            "Passion" -> setTheme(R.style.AppTheme_PASSION)
            "Joy" -> setTheme(R.style.AppTheme_JOY)
            else -> setTheme(R.style.Base_AppTheme)
        }
    }

}
