package com.iwmh.spodifyapp

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iwmh.spodifyapp.util.Util
import org.junit.Test
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.iwmh.spodifyapp.remote_data_source.LocalKeyValueStorageImpl
import com.iwmh.spodifyapp.remote_data_source.RemoteDataSourceImpl
import com.iwmh.spodifyapp.remote_data_source.WebApiClientImpl
import com.iwmh.spodifyapp.repository.MainRepository
import com.iwmh.spodifyapp.repository.MainRepositoryImpl
import com.iwmh.spodifyapp.repository.model.Secret
import com.iwmh.spodifyapp.util.Constants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.openid.appauth.AuthState

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import java.io.IOException
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@SmallTest
class InstrumentedUnitTest{

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

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun read_write_encrypted_shared_preferences(){

        // Context of the app under test.
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val mainKey = MasterKey.Builder(context)
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

        val remoteDataSource = RemoteDataSourceImpl(WebApiClientImpl(context), LocalKeyValueStorageImpl(prefs))

        val mainRepository = MainRepositoryImpl(remoteDataSource)

        val testValue = "test string"

        // Store data.
        mainRepository.storeData(Constants.shared_prefs_file, testValue)

        // Read data.
        val readValue = mainRepository.readData(Constants.shared_prefs_file)

        assertEquals(testValue, readValue)
    }



}