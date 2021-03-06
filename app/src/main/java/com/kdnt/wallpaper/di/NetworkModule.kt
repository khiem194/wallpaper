package com.kdnt.wallpaper.di

import com.google.gson.Gson
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.kdnt.wallpaper.BuildConfig
import com.kdnt.wallpaper.WallpaperUtils
import com.kdnt.wallpaper.data.api.ApiWallPaperService
import io.reactivex.functions.Consumer
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


val networkModule = module {
    single { getRetrofit().create(ApiWallPaperService::class.java) }
    single { provideOkHttpClient() }
}


fun getRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl(WallpaperUtils.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(provideOkHttpClient())
        .build()
}

fun provideOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request =
                chain.request().newBuilder().addHeader("lang", Locale.getDefault().language)
                    .build()
            chain.proceed(request)
        }
        .addInterceptor(
            LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BODY)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .addHeader("version", BuildConfig.VERSION_NAME)
                .build()
        )
    return builder.build()
}