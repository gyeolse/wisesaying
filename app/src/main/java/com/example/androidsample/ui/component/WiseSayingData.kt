package com.example.androidsample.ui.component

import androidx.annotation.DrawableRes
import java.util.Calendar
import java.util.Date

data class WiseSayingData(
    val uid: Int,
    val contents : String,
    val author : String,
    val isFavorite : Boolean,
    val isFavoriteAddDate : Long,
    val wiseSayingDataThemes : String,
    val isFlipped : Boolean = false,
)