package ru.myuniquenickname.myapplication.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "actors")
data class Actor(
    val id: Long,
    val name: String,
    val picturePath: String,
)
