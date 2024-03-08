package com.myothiha.appbase.exception

import com.myothiha.appbase.exception.UnidirectionalMap

interface ExceptionMapper : UnidirectionalMap<Throwable, String> {}
