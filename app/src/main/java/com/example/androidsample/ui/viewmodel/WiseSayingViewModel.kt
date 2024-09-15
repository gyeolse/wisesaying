package com.example.androidsample.ui.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsample.data.model.WiseSaying
import com.example.androidsample.domain.repository.WiseSayingDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.format
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class WiseSayingViewModel @Inject constructor(
    private val wiseSayingDataRepository: WiseSayingDataRepository,
) : ViewModel() {

    private val _wiseSayings = mutableStateOf<List<WiseSaying>>(emptyList())
    val wiseSayings: State<List<WiseSaying>> get() = _wiseSayings

//    private val _favoriteWiseSayings = mutableStateOf<List<WiseSaying>>(emptyList())
//    val favoriteWiseSayings: State<List<WiseSaying>> get() = _favoriteWiseSayings

//    private val _favoriteWiseSayings = MutableStateFlow<List<WiseSaying>>(emptyList())
//    val favoriteWiseSayings: StateFlow<List<WiseSaying>> = _favoriteWiseSayings

    private val _favoriteWiseSayings = MutableLiveData<List<WiseSaying>>(emptyList())
    val favoriteWiseSayings: LiveData<List<WiseSaying>> get() = _favoriteWiseSayings

    init {
        // viewModel 초기화 시 데이터를 로드
        fetchWiseSayings()
        fetchFavoriteWiseSayings()
    }

    suspend fun getAll() : List<WiseSaying> {
        return wiseSayingDataRepository.getAll()
    }

    suspend fun getFavoriteList() : List<WiseSaying> {
        return wiseSayingDataRepository.getFavoriteList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateIsFavorite(index: Int) {
        val wisesaying = _wiseSayings.value.find { WiseSaying -> WiseSaying.uid == index }

        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateTimeString = currentTime.format(formatter)

        if (wisesaying != null) {
            wisesaying.isFavoriteAddDate = dateTimeString
        }

        Log.d(TAG, " updateIsFavorite value idx=" + wisesaying?.uid)
        Log.d(TAG, " updateIsFavorite isFavorite=" + wisesaying?.isFavorite)

        wisesaying?.let {
            viewModelScope.launch {
                wiseSayingDataRepository.updateIsFavorite(it.copy(isFavorite = (if(wisesaying.isFavorite == 1) 0 else 1)).apply {
                    this.uid = it.uid
                })
                fetchFavoriteWiseSayings()
                fetchWiseSayings()
            }
        }
    }

    //  비동기적으로 명언을 가져와서 상태를 업데이트
    private fun fetchWiseSayings() {
        viewModelScope.launch {
            try {
                val sayings = getAll()
                _wiseSayings.value = wiseSayingDataRepository.getAll()
                Log.d("WiseSayingViewModel", "Wise sayings loaded: ${_wiseSayings.value.size} items")
            } catch(e: Exception) {
                Log.d("MyViewModel", "Failed to load wise sayings", e)
            }
        }
    }

    fun fetchFavoriteWiseSayings() {
        viewModelScope.launch {
            try {
                val newFavoriteWiseSayings = wiseSayingDataRepository.getFavoriteList()
                _favoriteWiseSayings.value = newFavoriteWiseSayings
//                Log.d(TAG, "fetchFavoriteWiseSayings - Updated favoriteWiseSayings: ${_favoriteWiseSayings.value.size}")
            } catch (e: Exception) {
                Log.d(TAG, "fail fetchFavoriteWiseSayings")
            }
        }
    }

    companion object {
        const val TAG =  "WiseSayingViewModel"
    }
}