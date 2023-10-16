package com.ferhatt.workoutapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ferhatt.workoutapp.R
import com.ferhatt.workoutapp.databinding.ActivityBmiBinding
import com.ferhatt.workoutapp.viewmodel.BMIViewModel
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private var binding : ActivityBmiBinding? = null
  // private lateinit var BMIViewModel : BMIViewModel
    val BMIViewModel : BMIViewModel by viewModels()
     private var bmi : Float ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)




        setSupportActionBar(binding?.toolbarBmiActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "CALCULATE BMI"
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
           // onBack pressed is dispatcher so we use onBackPressedDispatcher.onBackPressed
            onBackPressedDispatcher.onBackPressed()
        }

        try {
            BMIViewModel.makeVisibleUsUnitsView(binding!!.tilMetricUnitWeight,binding!!.tilMetricUnitHeight,binding!!.tilUsMetricUnitWeight,
                binding!!.tilMetricUsUnitHeightFeet,binding!!.tilMetricUsUnitHeightInch,binding!!.etUsMetricUnitHeightFeet,binding!!.etUsMetricUnitWeight,
                binding!!.etUsMetricUnitHeightInch,binding!!.llDiplayBMIResult)
        }catch (e : Exception) {
            e.printStackTrace()
        }


        try {

            binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->

                if (checkedId == R.id.rbMetricUnits) {
                    BMIViewModel.makeVisibleMetricUnitsView(binding!!.etMetricUnitHeight,binding!!.etMetricUnitWeight,
                        binding!!.tilMetricUnitWeight,binding!!.tilMetricUnitHeight,binding!!.tilUsMetricUnitWeight,
                        binding!!.tilMetricUsUnitHeightInch,binding!!.tilMetricUsUnitHeightFeet,binding!!.llDiplayBMIResult)
                } else {
                    BMIViewModel.makeVisibleUsUnitsView(binding!!.tilUsMetricUnitWeight,binding!!.tilMetricUnitHeight,
                        binding!!.tilUsMetricUnitWeight,binding!!.tilMetricUsUnitHeightFeet,binding!!.tilMetricUsUnitHeightInch,
                        binding!!.etUsMetricUnitWeight,binding!!.etUsMetricUnitHeightInch,binding!!.etUsMetricUnitHeightFeet,binding!!.llDiplayBMIResult)
                }
            }

        }catch (e : Exception){
            e.printStackTrace()
        }


        binding?.btnCalculateUnits?.setOnClickListener {
            BMIViewModel.calculateUnits(this@BMIActivity,binding!!.etMetricUnitHeight,binding!!.etMetricUnitWeight,
                binding!!.etUsMetricUnitHeightFeet,binding!!.etUsMetricUnitHeightInch,binding!!.etUsMetricUnitWeight,
                binding!!.llDiplayBMIResult,binding!!.tvBMIValue,binding!!.tvBMIType,binding!!.tvBMIDescription)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}