package com.ferhatt.workoutapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.ferhatt.workoutapp.WorkOutApp
import com.ferhatt.workoutapp.service.HistoryDao
import com.ferhatt.workoutapp.service.HistoryEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FinishViewModel : ViewModel() {

    fun dao(application: Application){
        val dao = (application as WorkOutApp).db.historyDao()
        addDateToDatabase(dao)
    }

    fun addDateToDatabase(historyDao: HistoryDao) {

        val c = Calendar.getInstance()
        val dateTime = c.time
        Log.e("Date : ", "" + dateTime)


        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault()) // Date Formatter
        val date = sdf.format(dateTime) // dateTime is formatted in the given format.
        Log.e("Formatted Date : ", "" + date) // Formatted date is printed in the log.

        viewModelScope.launch {
            historyDao.insert(HistoryEntity(date))
            Log.e(
                "Date : ",
                "Added..."
            )
        }
    }


}