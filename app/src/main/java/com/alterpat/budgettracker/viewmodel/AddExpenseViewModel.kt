package com.alterpat.budgettracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alterpat.budgettracker.data.ExpenseDataBase
import com.alterpat.budgettracker.data.dao.ExpenseDao
import com.alterpat.budgettracker.data.model.ExpenseEntity

class AddExpenseViewModel(val dao: ExpenseDao) : ViewModel() {

    suspend fun addExpense(expenseEntity: ExpenseEntity): Boolean {
        try {
            dao.insertExpense(expenseEntity)
            return true
        }catch (ex : Throwable){
            return false
        }
    }

}

class AddExpenseViewModelFactory(private val context: Context) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddExpenseViewModel::class.java)){
            val dao = ExpenseDataBase.getDataBase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return AddExpenseViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}