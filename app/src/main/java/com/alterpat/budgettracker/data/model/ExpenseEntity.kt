package com.alterpat.budgettracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expanse_table")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)

    val id: Int?,
    val title: String,
    val amount: Double,
    //val category: String,
    val date: Long,
    val type: String
)
