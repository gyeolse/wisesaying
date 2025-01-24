package com.seyefactory.wisespoon.ui.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seyefactory.wisespoon.data.model.WiseSaying
import com.seyefactory.wisespoon.domain.repository.WiseSayingDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class WiseSayingViewModel @Inject constructor(
    private val wiseSayingDataRepository: WiseSayingDataRepository,
) : ViewModel() {

    private val _wiseSayings = MutableStateFlow<List<WiseSaying>>(emptyList())
    val wiseSayings: StateFlow<List<WiseSaying>> = _wiseSayings.asStateFlow()
    private val _favoriteWiseSayings = MutableStateFlow<List<WiseSaying>>(emptyList())
    val favoriteWiseSayings: StateFlow<List<WiseSaying>> = _favoriteWiseSayings.asStateFlow()

    var searchResults by mutableStateOf(listOf<WiseSaying>())
        private set

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
                wiseSayingDataRepository.updateIsFavorite(
                    it.copy(isFavorite = if (it.isFavorite == 1) 0 else 1)
                )
                fetchFavoriteWiseSayings()
                fetchWiseSayings()
            }
        }
    }

    fun searchWiseSayings(query: String) {
        viewModelScope.launch {
            wiseSayingDataRepository.searchWiseSayings(query)
                .collect { results ->
                    searchResults = results
                }
        }
    }

    private fun fetchWiseSayings() {
        viewModelScope.launch {
            try {
                _wiseSayings.value = wiseSayingDataRepository.getAll()
            } catch (e: Exception) {
                Log.d("WiseSayingViewModel", "Failed to load wise sayings", e)
            }
        }
    }

    private fun fetchFavoriteWiseSayings() {
        viewModelScope.launch {
            try {
                _favoriteWiseSayings.value = wiseSayingDataRepository.getFavoriteList()
            } catch (e: Exception) {
                Log.d("WiseSayingViewModel", "Failed to load favorite wise sayings", e)
            }
        }
    }

    fun saveThemePreference(isDarkMode: Boolean) {
        Log.d(TAG, "saveThemePreference Called isDarkMode=${isDarkMode}")
        viewModelScope.launch {
            wiseSayingDataRepository.saveThemePreference(isDarkMode)
        }
    }

    fun getThemePreference(): Flow<Boolean> {
        Log.d(TAG, "getThemePreference Called")
        return wiseSayingDataRepository.getThemePreference()

    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun savePushNotificationPreference(isEnabled: Boolean) {
        Log.d(TAG, "savePushNotificationPreference Called isEnabled=${isEnabled}")
        viewModelScope.launch {
            wiseSayingDataRepository.savePushNotificationPreference(isEnabled)
        }
    }

    fun getPushNotificationPreference(): Flow<Boolean> {
        Log.d(TAG, "getPushNotificationPreference Called")
        return wiseSayingDataRepository.getPushNotificationPreference()
    }

    fun checkAndSyncNotificationSettings() {
        Log.d(TAG, "checkAndSyncNotificationSettings Called")
        CoroutineScope(Dispatchers.Default).launch {
            val isPushNotificationEnabled = wiseSayingDataRepository.getPushNotificationPreference().first() // DataStore에서 가져옴
            val isNotificationScheduled = wiseSayingDataRepository.isNotificationScheduled() // 현재 알림 상태 확인

            if (isPushNotificationEnabled && !isNotificationScheduled) {
                wiseSayingDataRepository.scheduleDailyNotification()
            }
            Log.d(TAG, "checkAndSyncNotificationSettings Called, value is $isNotificationScheduled And $isPushNotificationEnabled")
        }
    }
    companion object {
        const val TAG =  "WiseSayingViewModel"
    }
}