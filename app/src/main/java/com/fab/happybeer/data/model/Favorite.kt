package com.fab.happybeer.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = -1,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "image")
    val image_url: String? = "",

    @ColumnInfo(name = "tagline")
    val tagline: String = "",

    @ColumnInfo(name = "rating")
    val rating: Float = -1f
)

fun Beer.toFavorite(): Favorite =
    Favorite(
        this.id,
        this.name,
        this.image_url,
        this.tagline,
        this.rating
    )