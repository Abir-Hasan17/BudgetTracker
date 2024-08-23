package com.alterpat.budgettracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alterpat.budgettracker.data.dao.ExpenseDao
import com.alterpat.budgettracker.data.model.ExpenseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Database(entities = [ExpenseEntity::class], version = 1)
abstract class ExpenseDataBase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object{
        const val Database_Name = "expense_database"

        @JvmStatic
        fun getDataBase(context: Context): ExpenseDataBase{
            return Room.databaseBuilder(
                context,
                ExpenseDataBase::class.java,
                Database_Name
            ).addCallback(object : Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    InitBasicData(context)
                }

                fun InitBasicData(context: Context){
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = getDataBase(context).expenseDao()

                        dao.insertExpense(ExpenseEntity(id = 1, title = "Salary", amount = 5000.0, date = System.currentTimeMillis(), type = "Income" ))
                        dao.insertExpense(ExpenseEntity(id = 2, title = "Netflix", amount = 300.0, date = System.currentTimeMillis(), type = "Expense" ))
                        dao.insertExpense(ExpenseEntity(id = 3, title = "Food", amount = 3000.0, date = System.currentTimeMillis(), type = "Expense" ))
                        dao.insertExpense(ExpenseEntity(id = 4, title = "PayPal", amount = 2000.0, date = System.currentTimeMillis(), type = "Income" ))
                    }
                }
            }).build()
        }
    }
}