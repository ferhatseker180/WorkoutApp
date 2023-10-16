package com.ferhatt.workoutapp.viewmodel

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModel
import com.ferhatt.workoutapp.databinding.ActivityBmiBinding
import com.ferhatt.workoutapp.view.BMIActivity
import com.google.android.material.textfield.TextInputLayout
import java.math.BigDecimal
import java.math.RoundingMode

class BMIViewModel : ViewModel() {
    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var currentVisibleView: String = METRIC_UNITS_VIEW

     fun calculateUnits(context : Context, etMetricUnitHeight : AppCompatEditText, etMetricUnitWeight : AppCompatEditText,
                        etUsMetricUnitHeightFeet : AppCompatEditText, etUsMetricUnitHeightInch : AppCompatEditText,
                        etUsMetricUnitWeight : AppCompatEditText,llDiplayBMIResult: LinearLayout,
                        tvBMIValue : TextView,tvBMIType : TextView,tvBMIDescription : TextView ){

        if (currentVisibleView == METRIC_UNITS_VIEW) {
            if (validateMetricUnits(etMetricUnitHeight,etMetricUnitWeight)) {

                // The height value is converted to float value and divided by 100 to convert it to meter.
               // val heightValue: Float = etMetricUnitHeight?.text.toString().toFloat() / 100
                val heightValue: Float = etMetricUnitHeight?.text.toString().toFloat() / 100

                // The weight value is converted to float value
                val weightValue: Float = etMetricUnitWeight?.text.toString().toFloat()

                // BMI value is calculated in METRIC UNITS using the height and weight value.
                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResult(bmi,llDiplayBMIResult, tvBMIValue,tvBMIType,tvBMIDescription)
            } else {
                Toast.makeText(
                    context,
                    "Please enter valid values.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else {

            // The values are validated.
            if (validateUsUnits(etUsMetricUnitWeight,etUsMetricUnitHeightFeet,etUsMetricUnitHeightInch)) {

                val usUnitHeightValueFeet: String =
                    etUsMetricUnitHeightFeet?.text.toString()
                val usUnitHeightValueInch: String =
                    etUsMetricUnitHeightInch?.text.toString()
                val usUnitWeightValue: Float = etUsMetricUnitWeight?.text.toString()
                    .toFloat()


                val heightValue =
                    usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))

                displayBMIResult(bmi,llDiplayBMIResult, tvBMIValue,tvBMIType,tvBMIDescription)
            } else {
                Toast.makeText(
                    context,
                    "Please enter valid values.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }


     fun makeVisibleMetricUnitsView(etMetricUnitHeight : AppCompatEditText,etMetricUnitWeight : AppCompatEditText, tilMetricUnitWeight : TextInputLayout,
                                    tilMetricUnitHeight : TextInputLayout, tilUsMetricUnitWeight : TextInputLayout,tilMetricUsUnitHeightFeet : TextInputLayout,
                                    tilMetricUsUnitHeightInch : TextInputLayout, llDiplayBMIResult : LinearLayout) {

        currentVisibleView = METRIC_UNITS_VIEW
        tilMetricUnitWeight?.visibility = View.VISIBLE
        tilMetricUnitHeight?.visibility = View.VISIBLE
        tilUsMetricUnitWeight?.visibility = View.GONE
        tilMetricUsUnitHeightFeet?.visibility = View.GONE
        tilMetricUsUnitHeightInch?.visibility = View.GONE

        etMetricUnitHeight?.text!!.clear()
        etMetricUnitWeight?.text!!.clear()

        llDiplayBMIResult?.visibility = View.INVISIBLE
    }
    // END

     fun makeVisibleUsUnitsView(tilMetricUnitWeight : TextInputLayout,tilMetricUnitHeight : TextInputLayout,
                                tilUsMetricUnitWeight : TextInputLayout,tilMetricUsUnitHeightFeet : TextInputLayout,
                                tilMetricUsUnitHeightInch : TextInputLayout,etUsMetricUnitHeightFeet : AppCompatEditText,
                                etUsMetricUnitWeight : AppCompatEditText,etUsMetricUnitHeightInch : AppCompatEditText, llDiplayBMIResult : LinearLayout) {
        currentVisibleView = US_UNITS_VIEW
        tilMetricUnitHeight?.visibility = View.INVISIBLE
        tilMetricUnitWeight?.visibility = View.INVISIBLE
        tilUsMetricUnitWeight?.visibility = View.VISIBLE
        tilMetricUsUnitHeightFeet?.visibility = View.VISIBLE
        tilMetricUsUnitHeightInch?.visibility = View.VISIBLE

        etUsMetricUnitWeight?.text!!.clear()
        etUsMetricUnitHeightFeet?.text!!.clear()
        etUsMetricUnitHeightInch?.text!!.clear()

        llDiplayBMIResult?.visibility = View.INVISIBLE
    }


     fun validateMetricUnits(etMetricUnitWeight : AppCompatEditText,etMetricUnitHeight : AppCompatEditText): Boolean {
        var isValid = true

        if (etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (etMetricUnitHeight?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

     fun validateUsUnits(etUsMetricUnitWeight : AppCompatEditText,etUsMetricUnitHeightFeet : AppCompatEditText,etUsMetricUnitHeightInch : AppCompatEditText): Boolean {
        var isValid = true

        when {
            etUsMetricUnitWeight?.text.toString().isEmpty() -> {
                isValid = false
            }
            etUsMetricUnitHeightFeet?.text.toString().isEmpty() -> {
                isValid = false
            }
            etUsMetricUnitHeightInch?.text.toString().isEmpty() -> {
                isValid = false
            }
        }

        return isValid
    }

     fun displayBMIResult(bmi: Float,llDiplayBMIResult : LinearLayout,tvBMIValue : TextView,tvBMIType : TextView,tvBMIDescription : TextView) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        llDiplayBMIResult?.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

       tvBMIValue?.text = bmiValue
        tvBMIType?.text = bmiLabel
        tvBMIDescription?.text = bmiDescription
    }


}