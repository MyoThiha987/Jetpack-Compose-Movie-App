package com.myothiha.appbase.exception

import java.io.IOException


/**
 * @Author myothiha
 * Created 12/05/2023 at 10:29 AM.
 **/

data class NetworkException(
    val errorBody: String? = null,
    var errorCode: Int = 0
) : IOException()