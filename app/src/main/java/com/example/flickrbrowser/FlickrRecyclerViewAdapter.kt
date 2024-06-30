package com.example.flickrbrowser

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FlickrImageViewHolder(view: View): RecyclerView.ViewHolder(view){
    var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    var title1: TextView = view.findViewById(R.id.title1)

}
class FlickrRecyclerViewAdapter(private var photoList: List<Photo>) : RecyclerView.Adapter<FlickrImageViewHolder>() {
    private val TAG = "FlickrRecyclerViewAdapt"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
        Log.d(TAG, "onCreateViewHolder NEW VIEW REUQESTED")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)

        return FlickrImageViewHolder(view)
    }

    fun loadNewData(newPhotos: List<Photo>) {
        photoList = newPhotos
        notifyDataSetChanged()

    }

    fun getPhoto(position: Int): Photo? {
        return if (photoList.isNotEmpty()) photoList[position] else null
    }

    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
        val photoItem = photoList[position]
        Log.d(TAG, "nBindViewHolder")
       // Picasso.with(holder.thumbnail.context).load(photoItem.image).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(holder.thumbnail)
        Picasso.get().load(photoItem.image).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(holder.thumbnail)

        holder.title1.text = photoItem.title

    }

    override fun getItemCount(): Int {
        return if (photoList.isNotEmpty()) photoList.size else 0
    }
}