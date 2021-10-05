package com.iwmh.spodifyapp.remote_data_source

interface LocalStorage {

    // Store data.
    fun storeData(keyString: String, valueString: String)

    // Read data.
    fun readData(keyString: String): String?
}