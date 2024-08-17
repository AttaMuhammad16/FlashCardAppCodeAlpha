package com.atta.flashcardapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "flashcards")
data class FlashCardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val question: String,
    val answer: String
)

