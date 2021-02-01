package ru.myuniquenickname.myapplication.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_details")
data class MovieDetails(
    @PrimaryKey val id: Long,
    var like: Boolean,
    val backdrop: String?,

    @ColumnInfo(name = "minimum_age")
    val minimumAge: Int,

    val ratings: Float,
    val title: String,
    val overview: String,

    @ColumnInfo(name = "number_of_ratings")
    val numberOfRatings: Long,

    val runtime: Long,
    val genres: String,
)
