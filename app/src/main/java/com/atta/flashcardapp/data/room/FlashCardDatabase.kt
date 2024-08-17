package com.atta.flashcardapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.atta.flashcardapp.models.FlashCardEntity


@Database(entities = [FlashCardEntity::class], version = 1,exportSchema = false)
abstract class FlashCardDatabase : RoomDatabase() {
    abstract fun flashCardDao(): FlashCardDao
}
