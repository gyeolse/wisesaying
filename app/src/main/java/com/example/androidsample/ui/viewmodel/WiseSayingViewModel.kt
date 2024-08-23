package com.example.androidsample.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsample.data.model.WiseSaying
import com.example.androidsample.domain.repository.WiseSayingDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WiseSayingViewModel @Inject constructor(
    private val wiseSayingDataRepository: WiseSayingDataRepository,
) : ViewModel() {

    private val _wiseSayings = mutableStateOf<List<WiseSaying>>(emptyList())
    val wiseSayings: State<List<WiseSaying>> get() = _wiseSayings

    init {
        // viewModel 초기화 시 데이터를 로드
        fetchWiseSayings()
    }

    suspend fun getAll() : List<WiseSaying> {
        return wiseSayingDataRepository.getAll()
    }

    fun updateIsFavorite(index: Int) {
        val wisesaying = _wiseSayings.value.find { WiseSaying -> WiseSaying.uid == index }

        Log.d(TAG, " updateIsFavorite value idx=" + wisesaying?.uid)
        Log.d(TAG, " updateIsFavorite isFavorite=" + wisesaying?.isFavorite)

        wisesaying?.let {
            viewModelScope.launch {
                wiseSayingDataRepository.updateIsFavorite(it.copy(isFavorite = (if(wisesaying.isFavorite == 1) 0 else 1)).apply {
                    this.uid = it.uid
                })
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

    companion object {
        const val TAG =  "WiseSayingViewModel"
    }
}