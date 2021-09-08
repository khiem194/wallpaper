package com.kdnt.wallpaper

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.kdnt.wallpaper.core.logger.initLogger
import com.kdnt.wallpaper.di.networkModule
import com.kdnt.wallpaper.di.persistenceModule
import com.kdnt.wallpaper.di.repositoryModule
import com.kdnt.wallpaper.di.viewModelModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class WallpaperApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {

        }
        this.initLogger(BuildConfig.DEBUG)
        startKoin {
            androidContext(this@WallpaperApplication)
            androidLogger(
                if (BuildConfig.DEBUG) Level.DEBUG else Level.ERROR
            )
            modules(persistenceModule)
            modules(repositoryModule)
            modules(viewModelModule)
            modules(networkModule)
        }
        Kotpref.init(this)
    }
}