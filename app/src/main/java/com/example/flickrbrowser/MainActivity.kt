package com.example.flickrbrowser

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.flickrbrowser.databinding.ActivityMainBinding
import androidx.appcompat.widget.Toolbar;

class MainActivity : AppCompatActivity(), GetRawData.OnDownloadComplete, GetFlickrData.OnDataAvailable {
    private val TAG = "MainActivity"

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "OnCreate called")
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
        val url = createUri("https://api.flickr.com/services/feeds/photos_public.gne","android,oreo", "en-us", true)
        val getRawData = GetRawData(this)
        getRawData.execute(url)

        Log.d(TAG, "OnCreate ends")
    }
    private fun createUri(baseURL: String, searchCriteria: String, lang: String, matchAll: Boolean): String {
        Log.d(TAG, "createUrl starts")

        var uri = Uri.parse(baseURL)
        var builder = uri.buildUpon()
        builder = builder.appendQueryParameter("tags", searchCriteria)
        builder = builder.appendQueryParameter("tagmode", if (matchAll) "ALL" else "ANY")
        builder = builder.appendQueryParameter("format", "json")
        builder = builder.appendQueryParameter("nojsoncallback", "1")
        uri = builder.build()

        return uri.toString()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu called")
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }

    //    companion object {
//        private const val TAG = "MainActivity"
//    }
    override fun onDownloadComplete(data: String, status: DownloadStatus) {
        if (status == DownloadStatus.OK) {
            Log.d(TAG, "onDownloadComplete CALLED")
            val getFlickrJsonData = GetFlickrData(this)
        } else Log.d(TAG, "DownloadComplete failed with status $status")
    }

    override fun onDataAvailable(data: List<Photo>) {
        Log.d(TAG, "onDataAvailable called, data is $data")
        Log.d(TAG, "onDataAvailable ends")
    }

    override fun onError(exception: Exception) {
        Log.d(TAG, "onError called")
    }
}