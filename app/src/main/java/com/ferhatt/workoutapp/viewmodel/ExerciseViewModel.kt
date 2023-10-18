package com.ferhatt.workoutapp.viewmodel

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferhatt.workoutapp.R
import com.ferhatt.workoutapp.adapter.ExerciseStatusAdapter
import com.ferhatt.workoutapp.databinding.ActivityExerciseBinding
import com.ferhatt.workoutapp.databinding.DialogCustomBackConfirmationBinding
import com.ferhatt.workoutapp.models.ExerciseModel
import com.ferhatt.workoutapp.util.Constants
import com.ferhatt.workoutapp.view.FinishActivity
import java.util.Locale
import java.util.function.Function

class ExerciseViewModel : ViewModel() {

     var tts: TextToSpeech? = null
     var exerciseList : ArrayList<ExerciseModel> = Constants.defaultExerciseList()
     var player: MediaPlayer? = null
    

    fun textToSpeak(context: Context,listener: OnInitListener){

        tts = TextToSpeech(context, listener)

    }
    fun speakOut(text : String){
        try {
            tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun mediaPlayer(context: Context){

        try {
            val soundURI =
                Uri.parse("android.resource://eu.tutorials.a7_minutesworkoutapp/" + R.raw.press_start)
            player = MediaPlayer.create(context, soundURI)
            player?.isLooping = false // Sets the player to be looping or non-looping.
            player?.start() // Starts Playback.
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setupRestView(flRestView : FrameLayout,tvTitle : TextView,upcomingLabel : TextView,tvUpcomingExerciseName : TextView,
                      tvExerciseName : TextView, flExerciseView : FrameLayout,ivImage : ImageView){

        flRestView?.visibility = View.VISIBLE
        tvTitle?.visibility = View.VISIBLE
        upcomingLabel?.visibility = View.VISIBLE
        tvUpcomingExerciseName?.visibility = View.VISIBLE
        tvExerciseName?.visibility = View.INVISIBLE
        flExerciseView?.visibility = View.INVISIBLE
        ivImage?.visibility = View.INVISIBLE

    }



}
