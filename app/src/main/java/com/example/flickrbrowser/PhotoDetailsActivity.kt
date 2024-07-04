package com.example.flickrbrowser
import android.os.Bundle
import android.util.Log
import com.example.flickrbrowser.databinding.ActivityPhotoDetailsBinding
import com.squareup.picasso.Picasso

class PhotoDetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityPhotoDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activateToolbar(true)

        val photo: Photo? = intent.getParcelableExtra(PHOTO_TRANSFER)

        photo?.let {

            binding.contentPhotoDetails.photoTitle.text = resources.getString(R.string.photo_title_text, photo.title)
            binding.contentPhotoDetails.photoAuthor.text = photo.author
            binding.contentPhotoDetails.photoTags.text = resources.getString(R.string.photo_tags_text, photo.tags)
            Picasso.get().load(it.image).placeholder(R.drawable.placeholder).into(binding.contentPhotoDetails.photoImage)
        } ?: run {
            Log.e("PhotoDetailsActivity", "Photo data is missing!")
        }
    }
}