package com.iwmh.spodifyapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import com.iwmh.spodifyapp.util.Constants
import com.iwmh.spodifyapp.view.Greeting
import org.junit.Test
import com.iwmh.spodifyapp.view.MainActivity
import net.openid.appauth.AuthState
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.runner.RunWith

private const val LAUNCH_TIMEOUT = 5000L

class InstrumentedMediumTest{

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var mainKey: MasterKey

    // initial AuthState value stored in Preferences.
    private var initialAuthState: AuthState = AuthState()

    // Device for UI Automator
    lateinit var device: UiDevice

    @Before
    fun set(){
        // Setup the main key for EncryptedSharedPreferences.
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        mainKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        // Setup the UI device.
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

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


    @After
    fun end(){

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

    @Test
    fun first_startup_shows_auth_button(){
        // Auth button exists.
        composeTestRule.onNodeWithText("auth").assertExists()
    }

    /***
     * TODO: It can't navigate to the previous screen when using click() of Uiautomator,
     *       though this should be automated.
     */
    /*
    @Test
    fun auth_page_to_home_page(){
        // Click the auth button
        composeTestRule.onNodeWithText("auth").performClick()

        Thread.sleep(2000)

        // scroll to the end.
        device.swipe(device.displayWidth/2, device.displayHeight, device.displayWidth/2, 10, 2)

        Thread.sleep(2000)

        var button = device.findObject(
            UiSelector().text("CANCEL")
        )

        button.click()

        Thread.sleep(2000)

        composeTestRule.onNodeWithText("auth").assertExists()

    }
     */

}