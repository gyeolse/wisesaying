package com.example.androidsample.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wisesaying")
data class WiseSaying(
    @PrimaryKey(autoGenerate = true) val uid: Boolean = null == true,
    @ColumnInfo(name = "contents") val contents: String? = null,
    @ColumnInfo(name = "author") val author: String? = null,
)