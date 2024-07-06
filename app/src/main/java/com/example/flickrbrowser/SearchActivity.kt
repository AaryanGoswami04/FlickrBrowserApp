package com.example.flickrbrowser

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.flickrbrowser.databinding.ActivitySeachBinding
import kotlin.contracts.Returns

class SearchActivity : BaseActivity() {
    private val TAG = "SearchActivity"
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, ".onCreate starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seach)
        activateToolbar(true)
        Log.d(TAG, ".onCreate ENDS")
   }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu: STARTS")
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)

        searchView?.isIconified = false

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, ".onQueryTextSubmit: CALLED with query: $query")

                val sharePref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                sharePref.edit().putString(FLICKR_QUERY, query).apply()
                Log.d(TAG, "Query saved: ${sharePref.getString(FLICKR_QUERY, "")}")
                searchView?.clearFocus()

                finish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchView?.setOnCloseListener {
            finish()
            false
        }
        Log.d(TAG, "onCreateOptionsMenu: returning")
        return true

    }
}