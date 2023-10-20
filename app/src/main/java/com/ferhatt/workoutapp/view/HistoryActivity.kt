package com.ferhatt.workoutapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferhatt.workoutapp.service.HistoryDao
import com.ferhatt.workoutapp.WorkOutApp
import com.ferhatt.workoutapp.adapter.HistoryAdapter
import com.ferhatt.workoutapp.databinding.ActivityHistoryBinding
import com.ferhatt.workoutapp.viewmodel.FinishViewModel
import com.ferhatt.workoutapp.viewmodel.HistoryViewModel
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null
    val historyViewModel : HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistoryActivity)

        val actionbar = supportActionBar//actionbar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true) //set back button
            actionbar.title = "HISTORY" // Setting a title in the action bar.
        }

        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val dao = (application as WorkOutApp).db.historyDao()

        historyViewModel.getAllCompletedDates(dao,binding!!.tvHistory,binding!!.rvHistory,binding!!.tvNoDataAvailable,applicationContext)

    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}