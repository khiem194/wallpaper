package com.kdnt.wallpaper.data.model

import com.google.gson.annotations.SerializedName

data class CuratedPhotosModel(
    @SerializedName("page") val page : Int,
    @SerializedName("per_page") val per_page : Int,
    @SerializedName("photos") val photos : MutableList<PhotoModel>,
    @SerializedName("total_results") val total_results : Int,
    @SerializedName("prev_page") val prev_page : String,
    @SerializedName("next_page") val next_page : String
)