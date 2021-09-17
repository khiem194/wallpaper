package com.kdnt.wallpaper.data.api

import com.kdnt.wallpaper.WallpaperUtils.KEY_API
import com.kdnt.wallpaper.data.model.CuratedPhotosModel
import com.kdnt.wallpaper.data.model.SearchPhotosModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import kotlin.random.Random

interface ApiWallPaperService {
    @Headers("Authorization: $KEY_API")
    @GET("curated")
    fun getListPhoto(
        @Query("page") page : Int,
        @Query("per_page") per_page : Int,
    ) : Observable<CuratedPhotosModel>

    @Headers("Authorization: $KEY_API")
    @GET("search")
    fun searchListPhoto(
        @Query("query") query: String,
        @Query("page") page : Int ,
        @Query("per_page") per_page : Int = 500,
    ) : Observable<SearchPhotosModel>
}