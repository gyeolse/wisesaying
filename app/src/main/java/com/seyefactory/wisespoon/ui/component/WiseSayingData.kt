package com.seyefactory.wisespoon.ui.component

data class WiseSayingData(
    val uid: Int,
    val contents : String,
    val author : String,
    val isFavorite : Boolean,
    val isFavoriteAddDate : Long,
    val wiseSayingDataThemes : String,
    val isFlipped : Boolean = false,
)