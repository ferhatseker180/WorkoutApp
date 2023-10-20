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

        val tilMetricUnitWeight = binding!!.tilMetricUnitWeight
        val tilMetricUnitHeight = binding!!.tilMetricUnitHeight
        val tilUsMetricUnitWeight = binding!!.tilUsMetricUnitWeight
        val tilMetricUsUnitHeightFeet =  binding!!.tilMetricUsUnitHeightFeet
        val tilMetricUsUnitHeightInch = binding!!.tilMetricUsUnitHeightInch
        val etUsMetricUnitHeightFeet = binding!!.etUsMetricUnitHeightFeet
        val etUsMetricUnitWeight = binding!!.etUsMetricUnitWeight
        val etUsMetricUnitHeightInch = binding!!.etUsMetricUnitHeightInch
        val llDiplayBMIResult = binding!!.llDiplayBMIResult

        val etMetricUnitHeight = binding!!.etMetricUnitHeight
        val etMetricUnitWeight = binding!!.etMetricUnitWeight

        val tvBMIValue = binding!!.tvBMIValue
        val tvBMIType = binding!!.tvBMIType
        val tvBMIDescription = binding!!.tvBMIDescription


        try {
            BMIViewModel.makeVisibleUsUnitsView(tilMetricUnitWeight,tilMetricUnitHeight,tilUsMetricUnitWeight,
                tilMetricUsUnitHeightFeet,tilMetricUsUnitHeightInch,etUsMetricUnitHeightFeet,etUsMetricUnitWeight,
                etUsMetricUnitHeightInch,llDiplayBMIResult)
        }catch (e : Exception) {
            e.printStackTrace()
        }

        try {

            binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->

                if (checkedId == R.id.rbMetricUnits) {
                    BMIViewModel.makeVisibleMetricUnitsView(etMetricUnitHeight,etMetricUnitWeight,
                        tilMetricUnitWeight,tilMetricUnitHeight,tilUsMetricUnitWeight,
                        tilMetricUsUnitHeightInch,tilMetricUsUnitHeightFeet,llDiplayBMIResult)
                } else {
                    BMIViewModel.makeVisibleUsUnitsView(tilUsMetricUnitWeight,tilMetricUnitHeight,
                        tilUsMetricUnitWeight,tilMetricUsUnitHeightFeet,tilMetricUsUnitHeightInch,
                        etUsMetricUnitWeight,etUsMetricUnitHeightInch,etUsMetricUnitHeightFeet,llDiplayBMIResult)
                }
            }

        }catch (e : Exception){
            e.printStackTrace()
        }


        binding?.btnCalculateUnits?.setOnClickListener {
            BMIViewModel.calculateUnits(this@BMIActivity,etMetricUnitHeight,etMetricUnitWeight,
                etUsMetricUnitHeightFeet,etUsMetricUnitHeightInch,etUsMetricUnitWeight,
               llDiplayBMIResult,tvBMIValue,tvBMIType,tvBMIDescription)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}