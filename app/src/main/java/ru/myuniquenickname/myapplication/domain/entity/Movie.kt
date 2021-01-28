package ru.myuniquenickname.myapplication.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "movies",
//    foreignKeys = [
//        (ForeignKey(
//            entity = MovieType::class,
//            parentColumns = ["id"],
//            childColumns = ["type_movie_id"],
//            onDelete = CASCADE
//        ))
//    ]
)
data class Movie(
    @PrimaryKey val id: Long,
    var like: Boolean,
    val poster: String,
    val backdrop: String,
    val minimumAge: Int,
    val ratings: Float,
    val title: String,
    val overview: String,
    val numberOfRatings: Long,
    val genres: String,
)



