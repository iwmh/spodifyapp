package com.iwmh.spodifyapp

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.*
import com.iwmh.spodifyapp.util.Constants
import net.openid.appauth.AuthState
import org.hamcrest.Matchers.notNullValue
import org.junit.*

import org.junit.runner.RunWith
import org.junit.runner.manipulation.Ordering

private const val LAUNCH_TIMEOUT = 5000L

private const val WAIT_TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class InstrumentedMediumTest{

    companion object {

        // Device for UI Automator
        private lateinit var device: UiDevice

        // Key for EncryptedSharedPreferences.
        private lateinit var mainKey: MasterKey

        // initial AuthState value stored in Preferences.
        private var initialAuthState: AuthState = AuthState()

        private var packageName = "com.iwmh.spodifyapp"

        @BeforeClass
        @JvmStatic
        fun init() {

            // Setup the main key for EncryptedSharedPreferences.
            val context = ApplicationProvider.getApplicationContext<Context>()
            mainKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            // Backup the initial AuthState value.
            val prefs = EncryptedSharedPreferences.create(
                context,
                Constants.shared_prefs_file,
                mainKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

            val initialAuthStateString = prefs.getString(Constants.auth_state_json, "")
            if (!initialAuthStateString.isNullOrEmpty()) {
                initialAuthState = AuthState.jsonDeserialize(initialAuthStateString)
            }

            // Remove all the EncryptedSharedPreferences.
            prefs.edit()
                .clear()
                .apply()
        }

        @AfterClass
        @JvmStatic
        fun end() {

            // Restore the initial AuthState value.
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val prefs = EncryptedSharedPreferences.create(
                context,
                Constants.shared_prefs_file,
                mainKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            prefs.edit()
                .putString(Constants.auth_state_json, initialAuthState.jsonSerializeString())
                .apply()

        }
    }

    @Before
    fun startApp(){
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from the home screen
        device.pressHome()

        // Wait for launcher
        val launcherPackage: String = device.launcherPackageName
        assertThat(launcherPackage, notNullValue())
        device.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            LAUNCH_TIMEOUT
        )

        // Launch the app
        val intent = context.packageManager.getLaunchIntentForPackage(
            packageName
        )?.apply {
            // Clear out any previous instances
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(
            Until.hasObject(By.pkg(packageName).depth(0)),
            LAUNCH_TIMEOUT
        )
    }

    @Test
    fun auth_page_to_home_page(){

        var auth = device.findObject(
            UiSelector().text("auth")
        )
        auth.click()

        // Wait until the Spotify authorization page is shown.
        device.wait(
            Until.findObject(
                By.textContains("You agree that")
            ), WAIT_TIMEOUT
        )

        // scroll to the end.
        device.swipe(
            device.displayWidth/2,
            device.displayHeight - 100,
            device.displayWidth/2,
            100, 2)

        // Click AGREE button.
        var button = device.wait(
            Until.findObject(
                By.text("AGREE")
            ), WAIT_TIMEOUT
        )
        button.click()

        // auth button exists again
        device.wait(
            Until.findObject(
                By.text("auth")
            ), WAIT_TIMEOUT
        )
        var authAgain = device.findObject(
            UiSelector().text("auth")
        )
        assert(authAgain.exists())
    }





}