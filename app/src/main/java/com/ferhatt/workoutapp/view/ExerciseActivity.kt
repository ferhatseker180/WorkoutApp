package com.ferhatt.workoutapp.view

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
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferhatt.workoutapp.util.Constants
import com.ferhatt.workoutapp.R
import com.ferhatt.workoutapp.adapter.ExerciseStatusAdapter
import com.ferhatt.workoutapp.databinding.ActivityExerciseBinding
import com.ferhatt.workoutapp.databinding.DialogCustomBackConfirmationBinding
import com.ferhatt.workoutapp.models.ExerciseModel
import com.ferhatt.workoutapp.viewmodel.BMIViewModel
import com.ferhatt.workoutapp.viewmodel.ExerciseViewModel
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 10

    private var currentExercisePosition = -1

    private var binding: ActivityExerciseBinding? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    val exerciseViewModel : ExerciseViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        exerciseViewModel.textToSpeak(this@ExerciseActivity,this)

        setupRestView()

        setupExerciseStatusRecyclerView()
    }

    private fun setupRestView() {

        exerciseViewModel.mediaPlayer(applicationContext)

        val flRestView = binding!!.flRestView
        val tvTitle = binding!!.tvTitle
        val upcomingLabel = binding!!.upcomingLabel
        val tvUpcomingExerciseName =  binding!!.tvUpcomingExerciseName
        val tvExerciseName = binding!!.tvExerciseName
        val flExerciseView = binding!!.flExerciseView
        val ivImage = binding!!.ivImage


        exerciseViewModel.setupRestView(flRestView, tvTitle, upcomingLabel, tvUpcomingExerciseName, tvExerciseName, flExerciseView, ivImage)

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.tvUpcomingExerciseName?.text =
            exerciseViewModel.exerciseList[currentExercisePosition + 1].getName()

        setRestProgressBar()
    }

    private fun setRestProgressBar() {

        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++ // It is increased by 1
                binding?.progressBar?.progress =
                    10 - restProgress // Indicates progress bar progress
                binding?.tvTimer?.text =
                    (10 - restProgress).toString()  // Current progress is set to text view in terms of seconds.
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseViewModel.exerciseList[currentExercisePosition].setIsSelected(true)
                exerciseAdapter?.notifyDataSetChanged()

                setupExerciseView()
            }
        }.start()

    }

    private fun setupExerciseView() {

        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE
        binding?.upcomingLabel?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        exerciseViewModel.speakOut(exerciseViewModel.exerciseList[currentExercisePosition].getName())

        binding?.ivImage?.setImageResource(exerciseViewModel.exerciseList[currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseViewModel.exerciseList[currentExercisePosition].getName()

        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress =
                    exerciseTimerDuration.toInt() - exerciseProgress
                binding?.tvTimerExercise?.text =
                    (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {
                   if (currentExercisePosition < exerciseViewModel.exerciseList.size - 1){

                       exerciseViewModel.exerciseList[currentExercisePosition].setIsSelected(false)
                       exerciseViewModel.exerciseList[currentExercisePosition].setIsCompleted(true)

                    exerciseAdapter?.notifyDataSetChanged()
                    setupRestView()
                } else {
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }

        }.start()

    }

    public override fun onDestroy() {

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }


        if (exerciseViewModel.tts != null){
            exerciseViewModel!!.tts?.stop()
            exerciseViewModel!!.tts?.shutdown()
        }

        if(player != null){
            player!!.stop()
        }

        super.onDestroy()
        binding = null

    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts

            val result = exerciseViewModel.tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun setupExerciseStatusRecyclerView(){

        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseViewModel.exerciseList)

        binding?.rvExerciseStatus?.adapter = exerciseAdapter

    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)

        customDialog.setContentView(dialogBinding.root)

        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }


}