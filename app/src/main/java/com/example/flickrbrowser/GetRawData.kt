package com.example.flickrbrowser

import android.util.Log
import android.os.AsyncTask
import android.telephony.TelephonyCallback.DataActivityListener
import java.io.IOException
import java.io.ObjectOutput
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus {
    OK, IDLE, NOT_INITIALISED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR
}

class GetRawData (private val listener: OnDownloadComplete): AsyncTask<String, Void, String>() {
    private val TAG = "GetRawData"
    private var downloadStatus = DownloadStatus.IDLE

    interface OnDownloadComplete{
        fun onDownloadComplete(data: String, status: DownloadStatus)
    }
//    private var listener: MainActivity? = null
//
//    fun setDownloadCompleteListener(callbackObject: MainActivity){
//        listener = callbackObject
//    }
    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: String) {
        Log.d(TAG, "onPostExecute called")
        listener.onDownloadComplete(result, downloadStatus)
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


