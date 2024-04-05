package com.example.indian

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("intelligence") val intelligence: Int,
    @SerializedName("general_health") val generalHealth: Int,
    @SerializedName("grooming") val grooming: Int,
    @SerializedName("image_link") val imageLink: String,
    @SerializedName("name") val name: String,
    @SerializedName("origin") val origin: String
)
