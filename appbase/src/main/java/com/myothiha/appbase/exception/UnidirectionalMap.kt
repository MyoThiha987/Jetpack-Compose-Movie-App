package com.myothiha.appbase.exception

interface UnidirectionalMap<F, T> {
    fun map(item: F): T
}