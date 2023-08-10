package com.blackdiamondstudios.android.a7minworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.blackdiamondstudios.android.a7minworkoutapp.databinding.ActivityBmiBinding
import com.blackdiamondstudios.android.a7minworkoutapp.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object{
        private const val METRIC_UNITS_VIEW ="METRIC_UNIT_VIEW" //metric unit view
        private const val US_UNITS_VIEW = "US_UNIT_VIEW" //US unit view
    }
private var currentVisibleView: String =
    METRIC_UNITS_VIEW //a variable to hold a value to make a selected view visible
    private var binding : ActivityBmiBinding ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener{
            onBackPressed()
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }

        makeVisibleMetricUnitsView()

        //good to use the oncheck listener for radio groups
        binding?.rgUnits?.setOnCheckedChangeListener{ _, checkedId: Int->
            if(checkedId ==R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else {
                makeVisibleUsUnitsView()
            }
        }

    }

    private fun makeVisibleMetricUnitsView(){
currentVisibleView = METRIC_UNITS_VIEW
        binding?.titleMetricUnitWeight?.visibility = View.VISIBLE
        binding?.titleMetricUnitHeight?.visibility = View.VISIBLE

        binding?.titleUsMetricUnitWeight?.visibility = View.GONE
        binding?.titleMetricUsUnitHeightFeet?.visibility = View.GONE
        binding?.titleMetricUsUnitHeightInch?.visibility = View.GONE

        binding?.etMetricUnitHeight?.text!!.clear() //height value is cleared if it is
        binding?.etMetricUnitWeight?.text!!.clear() //weight value cleared if it is empty

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE


    }

    private fun makeVisibleUsUnitsView(){
        currentVisibleView = US_UNITS_VIEW
        binding?.titleMetricUnitWeight?.visibility = View.GONE
        binding?.titleMetricUnitHeight?.visibility = View.GONE

        binding?.titleUsMetricUnitWeight?.visibility = View.VISIBLE
        binding?.titleMetricUsUnitHeightFeet?.visibility = View.VISIBLE
        binding?.titleMetricUsUnitHeightInch?.visibility = View.VISIBLE

        binding?.etUsMetricUnitWeight?.text!!.clear()
        binding?.etUsMetricUnitHeightFeet?.text!!.clear()
        binding?.etUsMetricUnitHeightInch?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE


    }

    private fun displayBMIResult(bmi: Float ){

        val bmiLabel : String
        val bmiDescription: String
///using the compare to method to compare 15 -> zero to the actual value which is a float
        if(bmi.compareTo(15f) <= 0){
            bmiLabel= "Very severely underweight bro/sis"
            bmiDescription = "You dangerously need to better care for yourself and add on some weight bro/sis"
        } else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0 ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself bro/sis"
        } else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0 ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations!! You are in a good shape!"
        }else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0 ) {
            bmiLabel = "Slighty Overweight"
            bmiDescription = "Take care! You're starting to get plumpy"
        }else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0 ) {
            bmiLabel = "Obese Category!"
            bmiDescription = "Warning! You need to reduce your weight to a normal level bro/sis!"
        }else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0 ) {
            bmiLabel = "Severely Obese!"
            bmiDescription = "Serious Warning!! You need to really consider serious body workouts!"
        }else {
            bmiLabel = "Excessively Obese!"
            bmiDescription = "You're a step away to your grave bro/sis!!Adjust right now!!"
        }

        //rendering the value on to the text view in the right format suing a equally divided even display
val bmiValue = BigDecimal(bmi.toDouble())
    .setScale(2,RoundingMode.HALF_EVEN).toString()

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }else if
            (binding?.etMetricUnitHeight?.text.toString().isEmpty() ){
                isValid = false
            }
            return isValid
    }
    private fun calculateUnits(){
        if(currentVisibleView == METRIC_UNITS_VIEW){

            if (validateMetricUnits()) {
                val heightValue: Float = binding?.etMetricUnitHeight?.text.toString()
                    .toFloat() / 100 //tp convert the entered value to grams

                val weightValue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResult(bmi)
            } else {
                Toast.makeText(
                    this@BMIActivity, "Please enter valid Metric values ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }else {
            if(validateUsMetricUnits()){
                val usUnitHeightValueFeet: String =
                    binding?.etUsMetricUnitHeightFeet?.text.toString()
                val usUnitHeightValueInch: String =
                    binding?.etUsMetricUnitHeightInch?.text.toString()
                val usUnitWeightValue: Float =
                    binding?.etUsMetricUnitWeight?.text.toString().toFloat()

            val heightValue =
                usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12


                val bmi = 703 * ( usUnitWeightValue /(heightValue * heightValue))

            displayBMIResult(bmi)
            }else {
                Toast.makeText(
                    this@BMIActivity, "Please enter valid US metric values ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun validateUsMetricUnits(): Boolean {
        var isValid = true

        when {
         binding?.etUsMetricUnitWeight?.text.toString().isEmpty() ->{
             isValid = false
         }
            binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty() ->{
                isValid = false
            }
            binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty() ->{
                isValid = false
            }
        }
        return isValid
    }




    }