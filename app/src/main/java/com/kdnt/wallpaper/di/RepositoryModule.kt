package com.kdnt.wallpaper.di

import com.kdnt.wallpaper.data.repository.WallPaperRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { WallPaperRepository(get()) }
}
