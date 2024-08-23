package com.example.androidsample.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wisesaying")
data class WiseSaying(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    var uid: Int = 0, // Int는 기본적으로 notNull입니다.

    @ColumnInfo(name = "contents")
    val contents: String? = null, // nullable 필드

    @ColumnInfo(name = "author")
    val author: String? = null, // nullable 필드

    @ColumnInfo(name = "isFavorite", defaultValue = "0")
    val isFavorite: Int = 0 // 기본값이 0인 notNull 필드

//    @PrimaryKey(autoGenerate = true) var uid: Int?= null,
//    @ColumnInfo(name = "contents") val contents: String? = null,
//    @ColumnInfo(name = "author") val author: String? = null,
//    @ColumnInfo(name = "isFavorite") val isFavorite: Int? = null,
)

