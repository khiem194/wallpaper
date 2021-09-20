package com.kdnt.wallpaper.di

import com.kdnt.wallpaper.ui.category.CategoryViewModel
import com.kdnt.wallpaper.ui.home.HomeViewModel
import com.kdnt.wallpaper.ui.setwallpaper.SetWallpaperViewModel
import com.kdnt.wallpaper.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { SplashViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { SetWallpaperViewModel() }
    viewModel { CategoryViewModel(get()) }

}
