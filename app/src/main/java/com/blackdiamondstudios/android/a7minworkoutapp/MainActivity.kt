package com.blackdiamondstudios.android.a7minworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.FrameLayout
import android.widget.Toast
import com.blackdiamondstudios.android.a7minworkoutapp.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
   //making use of view Binding : first modify the gradle file to accept view binding and import in the main activity
   // then first create a binding object... in our case its called "binding"
    //then assgin this binding object to the onCreate method of our main activity in this case
    //access the xml main id name which is visible only in this activity
   // i.e view binding works only on the elemnts of the same activity... an enormous advantage to the "findViewById" method
   //it also greatly avoids crashes with respect to id co-interactions from diff activities ...
    private var binding: ActivityMainBinding?= null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //val flStartButton: FrameLayout = findViewById(R.id.flStart)
        //binding object can be used in a easier approach to access xml elements
        //second way to access xml elements using viewBinding while using the 'binding' Object

        binding?.flStart?.setOnClickListener{

            Toast.makeText(
                this@MainActivity,
                "Howard wants you to get ready!",
                Toast.LENGTH_LONG

            ).show()

           val intent = Intent(this, ExercisesActivity::class.java)
            startActivity(intent)

        }

        binding?.btnBMI?.setOnClickListener{

            Toast.makeText(
                this@MainActivity,
                "BMI CALCULATOR IS SET!",
                Toast.LENGTH_LONG

            ).show()

            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)

        }

        binding?.btnHistory?.setOnClickListener{

            Toast.makeText(
                this@MainActivity,
                "View your yoga history!",
                Toast.LENGTH_LONG

            ).show()

            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)

        }

    }
    //to un-assign the binding link to avoid memory  leaks after execution of the code
    override fun onDestroy() {
        super.onDestroy()
            binding = null
    }

}





