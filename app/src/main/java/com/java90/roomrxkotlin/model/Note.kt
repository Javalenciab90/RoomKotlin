package com.java90.roomrxkotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note (
    val noteId: Int,
    val title: String,
    val description: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
