package com.conan.kotlingank.bean

import com.google.gson.annotations.SerializedName
import java.util.*

data class GankEntity(
        @SerializedName("id")
        val id: String,
        @SerializedName("createdAt")
        val createdTime: Date,
        @SerializedName("desc")
        val desc: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("publishedAt")
        val publishedTime: Date,
        @SerializedName("source")
        val source: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("used")
        val used: Boolean,
        @SerializedName("who")
        val publisher: String,
        @SerializedName("images")
        val images: List<String>?) {

    fun hasImage(): Int {
        return images?.size ?: 0
    }
}