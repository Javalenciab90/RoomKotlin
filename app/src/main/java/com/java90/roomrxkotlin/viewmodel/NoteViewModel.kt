package com.java90.roomrxkotlin.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.java90.roomrxkotlin.model.Note
import com.java90.roomrxkotlin.repository.NoteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NoteViewModel @ViewModelInject constructor(
        private val repository: NoteRepository,
        @Assisted private val savedState: SavedStateHandle) : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    private val _listNotes = MutableLiveData<List<Note>>()
    val listNotes = _listNotes

    init {
        getNotes()
    }

    private fun getNotes() {
        repository.getAllNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { } // Loading
            .doOnTerminate { } // LoadingFinish
            .subscribe({
                _listNotes.postValue(it)
            },{
                // Throwable message
            }).let {
                compositeDisposable.add(it)
            }
    }

    fun addNote(note: Note) {
        repository.insertNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{

            }).let {
                compositeDisposable.add(it)
            }
    }

    fun deleteNote(note: Note) {
        repository.deleteNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getNotes()
            },{

            }).let {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

}