package com.blackdiamondstudios.android.a7minworkoutapp


import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.blackdiamondstudios.android.a7minworkoutapp.databinding.ActivityExercisesBinding
import com.blackdiamondstudios.android.a7minworkoutapp.databinding.DialogCustomBackConfirmationBinding
import java.util.*
import kotlin.collections.ArrayList

class  ExercisesActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    //we create the binding object with respect to the activity name xml which it will use... in this case we
    //refer to the ExercisesActivity xml binding elements
    private var binding: ActivityExercisesBinding? = null

    private var ExerciseTimer: CountDownTimer? = null
    private var ExerciseProgress = 0

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var ExerciseList : ArrayList<ExerciseModel>?= null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech ?= null

    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter ?= null

    private var restTimerDuration: Long = 1
    private var exerciseTimerDuration: Long = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        ExerciseList = Constants.defaultExerciseList()

        tts = TextToSpeech(this, this)
        binding?.toolbarExercise?.setNavigationOnClickListener {

                customDialogForBackButton()
        }

            setUpRestView()
//the setup exercise status is at the bottom so we are sure the getlist function has already being executed
        setupExerciseStatusRecyclerView()


    }
    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener{
            this@ExercisesActivity.finish()
            customDialog.dismiss()
        }
            dialogBinding.tvNo.setOnClickListener {
                customDialog.dismiss()
            }
        customDialog.show()
    }

private fun setupExerciseStatusRecyclerView(){
    binding?.rvExerciseStatus?.layoutManager =
        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)

    exerciseAdapter = ExerciseStatusAdapter(ExerciseList!!)
    binding?.rvExerciseStatus?.adapter = exerciseAdapter
}
    private fun setUpRestView(){
//setting the media player fot the restview part of the activity

          try
          {
              val soundURI = Uri.parse("android.resource://com.blackdiamondstudios.android.a7minworkoutapp/" +R.raw.bibanke)
              player = MediaPlayer.create(applicationContext, soundURI)
              player?.isLooping = false
              player?.start()


          }catch(e:Exception){
              e.printStackTrace()
          }



        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE





        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.tvUpcomingExerciseName?.text = ExerciseList!![currentExercisePosition + 1 ].getName()


        setRestProgressbar()
    }

    private fun setUpExerciseView(){
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE

        binding?.tvTitle?.text = "Next Yoga Exercise name"
        binding?.flExerciseView?.visibility = View.VISIBLE

        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE



        if(ExerciseTimer != null){
            ExerciseTimer?.cancel()
            ExerciseProgress = 0
        }
//TTS to the exercises
        speakOut(ExerciseList!![currentExercisePosition].getName())

        binding?.ivImage?.setImageResource(ExerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = ExerciseList!![currentExercisePosition].getName()


        setExerciseProgressbar()
    }

        // the function to define the timer ability to our tvTimer textview
        private fun setRestProgressbar() {
            // we first define a progress bariable of progress which is attributed to restProgress

            binding?.Progressbar?.progress = restProgress
// we create an object with the CountDownTimer variable type iwith interval of 1000 milliseconds(ie 1sec each) and interval of 1000millisecs(1sec)
            restTimer = object : CountDownTimer(restTimerDuration* 10000, 1000) {
                override fun onTick(p0: Long) {
                    //incrementing the restprogress timer by 1000millis each time i.e 1sec
                    restProgress++
                    binding?.Progressbar?.progress =
                        10 - restProgress //we decrement from the initial time displayed
                    binding?.tvTimer?.text =
                        (10 - restProgress).toString() //we display it to string since its initially an int
                }

                override fun onFinish() {
                    Toast.makeText(
                        this@ExercisesActivity,
                        "Here now we will start the exercise",
                        Toast.LENGTH_SHORT
                    ).show()
                    currentExercisePosition++

                        //updating the exercise position to the recycler view
                    ExerciseList!![currentExercisePosition].setIsSelected(true)

                    //to set the new data view on the recycler view data
                    exerciseAdapter!!.notifyDataSetChanged()

                    setUpExerciseView()
                }
//we need to always add the Start() method at the end of the function to launch the timer
            }.start()

        }

    //we are implementing the same method to display the exercise progress while being in the same activity
    //this is achieved by placing the initial restProgress window to be gone and invisible

    private fun setExerciseProgressbar() {
        // we first define a progress bariable of progress which is attributed to restProgress

        binding?.ProgressbarExercise?.progress = ExerciseProgress
// we create an object with the CountDownTimer variable type iwith interval of 1000 milliseconds(ie 1sec each) and interval of 1000millisecs(1sec)
        ExerciseTimer = object : CountDownTimer(exerciseTimerDuration* 30000, 1000) {
            override fun onTick(p0: Long) {
                //incrementing the restprogress timer by 1000millis each time i.e 1sec
                ExerciseProgress++
                binding?.ProgressbarExercise?.progress =
                    30 - ExerciseProgress //we decrement from the initial time displayed
                binding?.tvTimerExercise?.text =
                    (30 - ExerciseProgress).toString() //we display it to string since its initially an int
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExercisesActivity,
                    "Una 30 secs don finish oh bro/sis!!",
                    Toast.LENGTH_SHORT
                ).show()

                if(currentExercisePosition<ExerciseList?.size!! - 1){
//update the recycler view on which exercise the view is currrently showing
                    ExerciseList!![currentExercisePosition].setIsSelected(false)
                    ExerciseList!![currentExercisePosition].setIsCompleted(true)
                    //to set the new data view on the recycler view data
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestView()

                }else{
                    Toast.makeText(
                        this@ExercisesActivity,
                        "Youpi, U don finish the 7 min workout yoga workout!!",
                        Toast.LENGTH_SHORT
                    ).show()
                        finish()
                    val intent = Intent(this@ExercisesActivity ,FinishActivity::class.java)
                    startActivity(intent)
                }


            }
//we need to always add the Start() method at the end of the function to launch the timer
        }.start()

    }

        override fun onDestroy(){
            super.onDestroy()
            if(restTimer != null){
                restTimer?.cancel()
                restProgress = 0
            }

            if(ExerciseTimer != null ){
                ExerciseTimer?.cancel()
                ExerciseProgress = 0
            }
            if(tts!= null){
                tts!!.stop()
                tts!!.shutdown()
            }

            if(player != null){
                player!!.stop()
            }

            binding = null


        }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA
                || result == TextToSpeech.LANG_NOT_SUPPORTED
            )
                Log.e("TTS", "the language specified is not supported!")
        }else
        {
            Log.e("TTS", "Initialization failed ")
        }
    }

    private fun speakOut(text : String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "" )
    }
}