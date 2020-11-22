package com.java90.roomrxkotlin.db

import androidx.room.*
import com.java90.roomrxkotlin.model.Note
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun observeNotes() : Flowable<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(note: Note) : Completable

    @Delete
    fun deletePost(note: Note) : Completable

}