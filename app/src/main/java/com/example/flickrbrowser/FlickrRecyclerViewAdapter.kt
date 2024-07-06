package com.example.flickrbrowser

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

private const val VIEW_TYPE_PHOTO = 1
private const val VIEW_TYPE_EMPTY = 0

class FlickrImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    var title: TextView = view.findViewById(R.id.title)
}

class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var message: TextView = view.findViewById(R.id.empty_message)
}

class FlickrRecyclerViewAdapter(private var photoList: List<Photo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TAG = "FlickrRecyclerViewAdapt"

    override fun getItemViewType(position: Int): Int {
        return if (photoList.isEmpty()) VIEW_TYPE_EMPTY else VIEW_TYPE_PHOTO
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder NEW VIEW REQUESTED")
        return if (viewType == VIEW_TYPE_PHOTO) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)
            FlickrImageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.empty_view, parent, false)
            EmptyViewHolder(view)
        }
    }

    fun loadNewData(newPhotos: List<Photo>) {
        photoList = newPhotos
        notifyDataSetChanged()
    }

    fun getPhoto(position: Int): Photo? {
        return if (photoList.isNotEmpty()) photoList[position] else null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FlickrImageViewHolder) {
            val photoItem = photoList[position]
            Log.d(TAG, "onBindViewHolder: Photo title = ${photoItem.title}, Photo image = ${photoItem.image}")
            Picasso.get().load(photoItem.image).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(holder.thumbnail)
            holder.title.text = photoItem.title
        } else if (holder is EmptyViewHolder) {
            holder.message.setText(R.string.empty_photo)
        }
    }

    override fun getItemCount(): Int {
        return if (photoList.isNotEmpty()) photoList.size else 1
    }
}
