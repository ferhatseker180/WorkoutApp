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

class ExerciseViewModel : ViewModel() {

    private var tts: TextToSpeech? = null
     var exerciseList : ArrayList<ExerciseModel> = Constants.defaultExerciseList()



    fun textToSpeak(context: Context,listener: OnInitListener){

        tts = TextToSpeech(context, listener)



    }




}
