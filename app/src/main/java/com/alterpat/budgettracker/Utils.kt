package com.alterpat.budgettracker

import java.text.SimpleDateFormat
import java.util.Locale

object Utils {

    fun toHumanReadableDate(dateInMillis:Long) : String{
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormatter.format(dateInMillis)
    }

    fun toConventionalDecimalValue(d: Double) : String{
        return String.format("%.2f",d)
    }

}