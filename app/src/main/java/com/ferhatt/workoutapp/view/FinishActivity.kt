package com.ferhatt.workoutapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ferhatt.workoutapp.service.HistoryDao
import com.ferhatt.workoutapp.service.HistoryEntity
import com.ferhatt.workoutapp.WorkOutApp
import com.ferhatt.workoutapp.databinding.ActivityFinishBinding
import com.ferhatt.workoutapp.viewmodel.ExerciseViewModel
import com.ferhatt.workoutapp.viewmodel.FinishViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null

    val finishViewModel : FinishViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        setSupportActionBar(binding?.toolbarFinishActivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.btnFinish?.setOnClickListener {
            finish()
        }

        finishViewModel.dao(application)

    }



}