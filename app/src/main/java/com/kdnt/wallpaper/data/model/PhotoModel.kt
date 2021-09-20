package com.kdnt.wallpaper.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoModel(
    @SerializedName("id") val id : Int,
    @SerializedName("width") val width : Int,
    @SerializedName("height") val height : Int,
    @SerializedName("url") val url : String,
    @SerializedName("photographer") val photographer : String,
    @SerializedName("photographer_url") val photographer_url : String,
    @SerializedName("photographer_id") val photographer_id : Int,
    @SerializedName("avg_color") val avg_color : String,
    @SerializedName("src") val src : SourceModel,
    @SerializedName("liked") val liked : Boolean
) : Parcelable