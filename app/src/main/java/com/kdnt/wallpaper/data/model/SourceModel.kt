package com.kdnt.wallpaper.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourceModel(
    @SerializedName("original") val original : String,
    @SerializedName("large2x") val large2x : String,
    @SerializedName("large") val large : String,
    @SerializedName("medium") val medium : String,
    @SerializedName("small") val small : String,
    @SerializedName("portrait") val portrait : String,
    @SerializedName("landscape") val landscape : String,
    @SerializedName("tiny") val tiny : String
) : Parcelable
