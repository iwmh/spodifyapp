package com.iwmh.spodifyapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iwmh.spodifyapp.util.Util
import org.junit.Test
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.iwmh.spodifyapp.repository.model.Secret
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

import org.junit.Assert.*
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class UtilUnitTestWithContext {
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun loadJsonFromAsset_test(){

        // Context of the app under test.
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // get secret_test.json file from "assets" folder
        val secretString = Util.loadJSONFromAsset(context, "secret_test.json")

        // deserialize the json string to Secret data class
        val secretData = Json.decodeFromString<Secret>(secretString ?: "")

        assertEquals( "test_client_id", secretData.client_id)
        assertEquals( "test_redirect_url", secretData.redirect_url)
    }
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun loadJsonFromAsset_test_no_file_found(){

        assertThrows(IOException::class.java) {

            // Context of the app under test.
            val context = InstrumentationRegistry.getInstrumentation().targetContext

            // get secret_test.json file from "assets" folder
            val secretString = Util.loadJSONFromAsset(context, "secret_test_.json")

        }
    }

}