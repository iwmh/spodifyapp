package com.iwmh.spodifyapp.util

import android.content.Context
import java.io.IOException

class Util {
    companion object {
        fun loadJSONFromAsset(context: Context, fileName: String): String? {
            val json: String? = try {
                val inputStream = context.assets.open(fileName)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                String(buffer, Charsets.UTF_8)
            } catch (ex: IOException) {
                ex.printStackTrace()
                return null
            }
            return json
        }
    }
}