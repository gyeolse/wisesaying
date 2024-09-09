package com.example.androidsample.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wisesaying")
data class WiseSaying(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    var uid: Int = 0, // Int는 기본적으로 notNull

    @ColumnInfo(name = "contents") val contents: String, // Not null

    @ColumnInfo(name = "author") val author: String, // Not null

    @ColumnInfo(name = "isFavoriteAddDate") val isFavoriteAddDate: String?,
    @ColumnInfo(name = "wisesayingTheme") val wisesayingTheme: Int?,
    @ColumnInfo(name = "isFavorite") val isFavorite: Int? = 0   // 기본값 0
)

