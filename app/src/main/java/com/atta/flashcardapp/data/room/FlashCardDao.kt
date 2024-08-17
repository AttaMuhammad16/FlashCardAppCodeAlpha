package com.atta.flashcardapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.atta.flashcardapp.models.FlashCardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashCard(flashCard: FlashCardEntity)

    @Update
    suspend fun updateFlashCard(flashCard: FlashCardEntity)

    @Delete
    suspend fun deleteFlashCard(flashCard: FlashCardEntity)

    @Query("DELETE FROM flashcards")
    suspend fun deleteAllFlashCards()  // New method to delete all flashcards


    @Query("SELECT * FROM flashcards")
    suspend fun getAllFlashCards(): List<FlashCardEntity>


}
