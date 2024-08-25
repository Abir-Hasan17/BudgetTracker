package com.alterpat.budgettracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alterpat.budgettracker.data.ExpenseDataBase
import com.alterpat.budgettracker.data.dao.ExpenseDao
import com.alterpat.budgettracker.data.model.ExpenseEntity

class HomeViewModel(dao: ExpenseDao) : ViewModel() {

    val expense = dao.getAllExpenses()

    fun getBalance(list : List<ExpenseEntity>) : String{
        var total = 0.0
        list.forEach{
            if(it.type == "Income") total+=it.amount
            else total-=it.amount
        }
        return "$total tk"
    }

    fun getTotalExpense(list : List<ExpenseEntity>) : String{
        var total = 0.0
        list.forEach{
            if(it.type == "Expense") total+=it.amount
        }
        return "$total tk"
    }

    fun getTotalIncome(list : List<ExpenseEntity>) : String{
        var total = 0.0
        list.forEach{
            if(it.type == "Income") total+=it.amount
        }
        return "$total tk"
    }
}


class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            val dao = ExpenseDataBase.getDataBase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}