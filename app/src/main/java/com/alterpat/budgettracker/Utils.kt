package com.alterpat.budgettracker

import java.text.SimpleDateFormat
import java.util.Locale

object Utils {

    fun toHumanReadableDate(dateInMillis:Long) : String{
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormatter.format(dateInMillis)
    }

}