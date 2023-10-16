package com.ferhatt.workoutapp

import android.app.Application
import com.ferhatt.workoutapp.service.HistoryDatabase

class WorkOutApp : Application() {

    val db: HistoryDatabase by lazy {
        HistoryDatabase.getInstance(this)
    }
}