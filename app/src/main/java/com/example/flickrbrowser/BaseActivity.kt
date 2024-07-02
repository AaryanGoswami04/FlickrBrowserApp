package com.example.flickrbrowser
import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.MaterialToolbar
import androidx.appcompat.app.AppCompatActivity

internal const val FLICKR_QUERY = "FLICKR_QUERY"
internal const val PHOTO_TRANSFER = "PHOTO_TRANSFER"

@SuppressLint("Registered")
open class BaseActivity: AppCompatActivity() {
    private val TAG = "BaseActivity"

    internal fun activateToolbar(enableHome: Boolean){
        Log.d(TAG, ".activateToolbar")
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}