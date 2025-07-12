package com.example.flickrbrowser

import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.flickrbrowser.databinding.ActivityPhotoDetailsBinding

class PhotoDetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityPhotoDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activateToolbar(true)

        val photo: Photo? = intent.getParcelableExtra(PHOTO_TRANSFER)

        photo?.let {
            binding.contentPhotoDetails.photoTitle.text =
                resources.getString(R.string.photo_title_text, it.title)

            binding.contentPhotoDetails.photoAuthor.text = it.author

            binding.contentPhotoDetails.photoTags.text =
                resources.getString(R.string.photo_tags_text, it.tags)

            Glide.with(this)
                .load(it.image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(binding.contentPhotoDetails.photoImage)
        } ?: run {
            Log.e("PhotoDetailsActivity", "Photo data is missing!")
        }
    }
}
