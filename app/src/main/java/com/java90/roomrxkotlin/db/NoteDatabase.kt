package com.java90.roomrxkotlin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.java90.roomrxkotlin.model.Note


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao() : NoteDao
}