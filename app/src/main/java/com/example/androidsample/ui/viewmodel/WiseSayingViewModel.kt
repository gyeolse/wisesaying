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
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WiseSayingViewModel @Inject constructor(
    private val wiseSayingDataRepository: WiseSayingDataRepository,
) : ViewModel() {
//    private val _items = mutableStateOf(emptyList<WiseSaying>())
//    val items: State<List<WiseSaying>> = _items

    private val _wiseSayings = mutableStateOf<List<WiseSaying>>(emptyList())
    val wiseSayings: State<List<WiseSaying>> get() = _wiseSayings

    init {
        // viewModel 초기화 시 데이터를 로드
        fetchWiseSayings()
        viewModelScope.launch {
            wiseSayingDataRepository.observeWiseSaying()
                .collect {
                    wisesayings ->
                    _wiseSayings.value = wisesayings
                }
        }
    }

    suspend fun getAll() : List<WiseSaying> {
        return wiseSayingDataRepository.getAll()
    }

    //  비동기적으로 명언을 가져와서 상태를 업데이트
    fun fetchWiseSayings() {
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
}