package com.muhammedensarisik.tubitak_gitaos.service

import com.muhammedensarisik.tubitak_gitaos.model.SpotStatus
import retrofit2.Call
import retrofit2.http.GET

interface OtoparkAPI {
    @GET("spot_status")
    fun getData(): Call<SpotStatus>
}