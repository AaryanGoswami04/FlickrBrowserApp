package com.example.flickrbrowser

import android.util.Log
import android.os.AsyncTask
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus {
    OK, IDLE, NOT_INITIALISED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR
}

class GetRawData : AsyncTask<String, Void, String>() {
    private val TAG = "GetRawData"
    private var downloadStatus = DownloadStatus.IDLE

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: String?) {
        Log.d(TAG, "onPostExecute called, parameter is $result")
    }

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: String?): String {
        if (params[0] == null) {
            downloadStatus = DownloadStatus.NOT_INITIALISED
            return "No URL Specified"
        }
        try {
            downloadStatus = DownloadStatus.OK
            return URL(params[0]).readText()
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is MalformedURLException -> {
                    downloadStatus = DownloadStatus.NOT_INITIALISED
                    "doInBackground: invalid URL ${e.message}"
                }

                is IOException -> {
                    downloadStatus = DownloadStatus.FAILED_OR_EMPTY
                    "doInBackground: IO Exception reading data: ${e.message}"
                }

                is SecurityException -> {
                    downloadStatus = DownloadStatus.PERMISSIONS_ERROR
                    "doInBackground: Security Exception: needs permission? ${e.message}"
                }

                else -> {
                    downloadStatus = DownloadStatus.ERROR
                    "download error: ${e.message}"
                }
            }
            Log.e(TAG, errorMessage)
            return errorMessage
        }
    }

}


