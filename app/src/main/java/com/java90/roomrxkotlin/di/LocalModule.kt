package com.java90.roomrxkotlin.di

import android.content.Context
import androidx.room.Room
import com.java90.roomrxkotlin.db.NoteDao
import com.java90.roomrxkotlin.db.NoteDatabase
import com.java90.roomrxkotlin.utils.Constants.NAME_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext app: Context) : NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NAME_DATABASE
        ).build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(db: NoteDatabase) : NoteDao {
        return db.noteDao()
    }
}