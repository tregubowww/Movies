package ru.myuniquenickname.myapplication.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "actors",
    primaryKeys = ["id", "movie_details_id"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDetails::class,
            parentColumns = ["id"],
            childColumns = ["movie_details_id"],
            onDelete = CASCADE
        )
    ]
) data class Actor(
    val id: Long,
    val name: String,
    val picturePath: String,

    @ColumnInfo(name = "movie_details_id")
    val movieDetailsId: Long
)
