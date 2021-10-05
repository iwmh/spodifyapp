package com.iwmh.spodifyapp.repository

interface MainRepository {

    fun storeData(keyString: String, valueString: String)

    fun readData(keyString: String): String?
}
