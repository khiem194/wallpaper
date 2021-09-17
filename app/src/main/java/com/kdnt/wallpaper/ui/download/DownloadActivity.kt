package com.kdnt.wallpaper.ui.download

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kdnt.wallpaper.R
import com.kdnt.wallpaper.ui.favourites.FavouritesActivity

class DownloadActivity : AppCompatActivity() {
    companion object {
        fun openActivity(context: Context): Intent = Intent(context, DownloadActivity::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
    }
}