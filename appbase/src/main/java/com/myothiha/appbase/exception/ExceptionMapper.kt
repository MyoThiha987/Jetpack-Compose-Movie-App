package com.myothiha.appbase.exception

import com.mth.appbase.exception.UnidirectionalMap

interface ExceptionMapper : UnidirectionalMap<Throwable, String> {}
