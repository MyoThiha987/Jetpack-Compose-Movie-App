package com.myothiha.appbase.base

import androidx.lifecycle.ViewModel
import com.myothiha.appbase.exception.ExceptionMapper
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 05/03/2024 at 9:58 PM.
 **/
abstract class BaseViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var exception: ExceptionMapper
}