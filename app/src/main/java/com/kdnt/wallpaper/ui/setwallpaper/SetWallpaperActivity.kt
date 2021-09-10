package com.kdnt.wallpaper.ui.setwallpaper

import android.app.WallpaperManager
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.kdnt.wallpaper.R
import com.kdnt.wallpaper.WallpaperUtils
import com.kdnt.wallpaper.core.base.BaseActivity
import com.kdnt.wallpaper.data.model.PhotoModel
import com.kdnt.wallpaper.databinding.ActivitySetWallpaperBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException


class SetWallpaperActivity : BaseActivity<SetWallpaperViewModel, ActivitySetWallpaperBinding>() {
    override fun getLayoutResId(): Int = R.layout.activity_set_wallpaper

    override val mViewModel: SetWallpaperViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val photoModel = intent.extras?.get(WallpaperUtils.KEY_PHOTOS) as PhotoModel
        Glide.with(this)
            .load(photoModel.src.large2x)
            .thumbnail(0.1f)
            .transition(DrawableTransitionOptions.withCrossFade(200))
            .dontAnimate()
            .into(mViewBinding.ivPhoto)
        mViewBinding.btnSetWallpaper.setOnClickListener {
            setWallpaper()
        }
    }

    private fun setWallpaper() {
        //this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN )
        val wallpaperManager = WallpaperManager.getInstance(this)
        try {
            val bitmap = mViewBinding.ivPhoto.drawable.toBitmap()
            wallpaperManager.setBitmap(bitmap)
            Toast.makeText(this, "Set Wallpaper Done!", Toast.LENGTH_SHORT).show()
        }
        catch (e : IOException){
            e.printStackTrace()
            Toast.makeText(this, "Can not set Wallpaper!", Toast.LENGTH_SHORT).show()
        }
    }
}