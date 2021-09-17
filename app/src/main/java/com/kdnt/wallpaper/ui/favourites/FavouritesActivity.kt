package com.kdnt.wallpaper.ui.favourites

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kdnt.wallpaper.R
import com.kdnt.wallpaper.ui.home.HomeActivity

class FavouritesActivity : AppCompatActivity() {
    companion object {
        fun openActivity(context: Context): Intent = Intent(context, FavouritesActivity::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)
    }
}