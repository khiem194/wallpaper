package com.kdnt.wallpaper.data.repository

import com.kdnt.wallpaper.data.api.ApiWallPaperService
import com.kdnt.wallpaper.data.model.CuratedPhotosModel
import com.kdnt.wallpaper.data.model.SearchPhotosModel
import io.reactivex.Observable

class WallPaperRepository(private val apiWallPaperService: ApiWallPaperService) {
    fun getListPhoto(page : Int): Observable<CuratedPhotosModel> = apiWallPaperService.getListPhoto(page)
    fun searchListPhoto(name : String) : Observable<SearchPhotosModel> = apiWallPaperService.searchListPhoto(name)
}