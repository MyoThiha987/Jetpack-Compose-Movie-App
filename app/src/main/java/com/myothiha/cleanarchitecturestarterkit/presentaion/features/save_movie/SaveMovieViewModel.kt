package com.myothiha.cleanarchitecturestarterkit.presentaion.features.save_movie

import androidx.lifecycle.viewModelScope
import com.myothiha.appbase.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 15/03/2024 at 2:25 PM.
 **/

@HiltViewModel
class SaveMovieViewModel  @Inject constructor(): BaseViewModel(){

    fun fetchSavedMoviesFromCache(){
        viewModelScope.launch {

        }
    }
}