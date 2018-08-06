package com.conan.kotlingank.data.network

import com.conan.kotlingank.bean.GankList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GankApi {

    enum class GankDataType(val dataType: String){
        DATA_TYPE_ALL("all"), DATA_TYPE_ANDROID("Android"),
        DATA_TYPE_IOS("iOS"), DATA_TYPE_WELFARE("福利"),
        DATA_TYPE_BREAK_VIDEO("休息视频"), DATA_TYPE_EXPAND_RESOURCE("拓展资源"),
        DATA_TYPE_FRONT("前端");
    }

    @GET("data/{type}/{pageSize}/{pageIndex}")
    fun getGankList(@Path("type") type: String, @Path("pageSize") pageSize: Int, @Path("pageIndex") pageIndex: Int): Observable<GankList>
}