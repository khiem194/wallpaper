package com.kdnt.wallpaper.ui.search.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.kdnt.wallpaper.data.model.PhotoModel
import com.kdnt.wallpaper.databinding.ItemWallpaperBinding

class SearchPhotoAdapter(private val mListPhoto: MutableList<PhotoModel>) :
    RecyclerView.Adapter<SearchPhotoAdapter.PhotosViewHolder>() {
    var onClickItemPhotoModel: ((photoModel: PhotoModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(
            ItemWallpaperBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        Glide.with(holder.binding.image)
            .load(mListPhoto[position].src.large)
            .thumbnail(0.1f)
            .transition(DrawableTransitionOptions.withCrossFade(200))
            .dontAnimate()
            .into(holder.binding.image)

        holder.itemView.setOnClickListener {
            onClickItemPhotoModel?.invoke(mListPhoto[holder.layoutPosition])
        }
    }

    override fun getItemCount(): Int = mListPhoto.size

    inner class PhotosViewHolder(val binding: ItemWallpaperBinding) :
        RecyclerView.ViewHolder(binding.root)
}