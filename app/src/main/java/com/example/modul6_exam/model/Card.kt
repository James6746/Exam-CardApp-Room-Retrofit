package com.example.modul6_exam.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class Card (
    val card_holder: String,
    val card_number: String,
    val expired_date: String,
    var is_exsist: Boolean,
    @PrimaryKey
    val id: Int? = null,
        )