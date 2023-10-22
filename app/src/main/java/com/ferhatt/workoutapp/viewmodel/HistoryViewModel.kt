package com.ferhatt.workoutapp.viewmodel

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ferhatt.workoutapp.WorkOutApp
import com.ferhatt.workoutapp.adapter.HistoryAdapter
import com.ferhatt.workoutapp.service.HistoryDao
import kotlinx.coroutines.launch

class HistoryViewModel() : ViewModel() {


    fun getAllCompletedDates(historyDao: HistoryDao,tvHistory : TextView,rvHistory : RecyclerView,
                             tvNoDataAvailable : TextView,context: Context) {

        viewModelScope.launch {
            historyDao.fetchALlDates().collect { allCompletedDatesList->

                if (allCompletedDatesList.isNotEmpty()) {
                    tvHistory?.visibility = View.VISIBLE
                    rvHistory?.visibility = View.VISIBLE
                    tvNoDataAvailable?.visibility = View.GONE

                    rvHistory?.layoutManager = LinearLayoutManager(context)

                    val dates = ArrayList<String>()
                    for (date in allCompletedDatesList){
                        dates.add(date.date)
                    }
                    val historyAdapter = HistoryAdapter(ArrayList(dates))

                    rvHistory?.adapter = historyAdapter
                } else {
                   tvHistory?.visibility = View.GONE
                    rvHistory?.visibility = View.GONE
                    tvNoDataAvailable?.visibility = View.VISIBLE
                }
            }
        }

    }


}