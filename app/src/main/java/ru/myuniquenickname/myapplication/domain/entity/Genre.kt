package ru.myuniquenickname.myapplication.domain.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

data class Genre (
    val id: Long,
    val name: String,
)
