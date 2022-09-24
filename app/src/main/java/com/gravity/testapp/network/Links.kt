package com.gravity.testapp.network

import com.squareup.moshi.Json

data class Links(
    @Json(name = "link") val link: String,
    @Json(name = "home") val home: String,
)
