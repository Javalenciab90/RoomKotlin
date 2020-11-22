package com.java90.roomrxkotlin.repository

import com.java90.roomrxkotlin.db.NoteDao
import com.java90.roomrxkotlin.model.Note
import com.java90.roomrxkotlin.utils.DataEventType
import com.java90.roomrxkotlin.utils.DatabaseEvent
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    private val mObserverSubject = PublishSubject.create<DatabaseEvent<Note>>()

    fun getAllNotes() : Flowable<List<Note>> {
        return noteDao.observeNotes()
            .map { it }
    }

    fun insertNote(note: Note) : Completable {
        val insertEvent = DatabaseEvent(DataEventType.INSERTED, note)
        return noteDao.insertPost(note)
                .doOnComplete { mObserverSubject.onNext(insertEvent) }
    }

    fun deleteNote(note: Note) : Completable {
        val deleteEvent = DatabaseEvent(DataEventType.REMOVED, note)
        return noteDao.deletePost(note)
            .doOnComplete { mObserverSubject.onNext(deleteEvent) }
    }

}