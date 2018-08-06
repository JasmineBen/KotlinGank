package com.conan.kotlingank

import android.content.Context
import com.conan.kotlingank.data.network.GankApi
import io.reactivex.disposables.CompositeDisposable
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    fun parseGankDataType(type: String): GankApi.GankDataType {
        for (dataType in GankApi.GankDataType.values()) {
            if (dataType.dataType.equals(type)) {
                return dataType
            }
        }
        return GankApi.GankDataType.DATA_TYPE_ALL
    }

    fun cancelRxDisposables(disposable: CompositeDisposable) {
        disposable.clear()
    }

    fun getPageIndex(currentCount: Int, pageSize: Int): Int {
        return currentCount / pageSize + 1
    }

    fun parseSimpleDate(date: Date?): String {
        if (date == null) {
            return ""
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(date)
    }

    fun buildRequestImageParam(context: Context, url: String, dpSize: Int): String {
        return url + "?imageView2/0/w/" + dpToPx(context, dpSize.toFloat())
    }


    fun dpToPx(context: Context, dp: Float): Int {
        return (context.resources.displayMetrics.density * dp + 0.5).toInt()
    }

    fun getColumnWidth(context: Context, columns: Int, itemDecoration: Int): Int {
        return (getScreenWith(context) - (columns + 1) * itemDecoration) / columns
    }

    private fun getScreenWith(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }
}