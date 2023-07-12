package workoutApp.myapplication

import android.app.Dialog
import android.content.Context
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
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import workoutApp.myapplication.databinding.ActivityExercise2Binding
import workoutApp.myapplication.databinding.ActivityMainBinding
import workoutApp.myapplication.databinding.DialogCustomBackConfirmationBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class exerciseActivity2 : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityExercise2Binding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<Model>? = null
    private var currentExercisePosition = -1

    private var speakText: TextToSpeech? = null
    private var mediaPlayer: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    private var restTimerDuration: Long = 5
    private var exerciseTimerDuration: Long = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercise2Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercies)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "7 Minutes Workout"
        }
        binding?.toolbarExercies?.setNavigationOnClickListener{
            customDialogForBackBtn()
        }

        exerciseList = Constants.defaultExerciseList()

        speakText = TextToSpeech(this, this)

        setUpRestView()
        setUpExerciseStatusRecyclerView()
    }

    override fun onBackPressed() {
        customDialogForBackBtn()
    }

    private fun customDialogForBackBtn(){
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)

        customDialog.setCanceledOnTouchOutside(false)
        customDialog.dialog_yes.setOnClickListener {
            this@exerciseActivity2.finish()
            customDialog.dismiss()
        }
        customDialog.dialog_no.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun setUpRestView(){

        try{
            val soundURI = Uri.parse(
                "android.resource://workoutApp.myapplication/" + R.raw.second)
            mediaPlayer = MediaPlayer.create(applicationContext, soundURI)
            mediaPlayer?.isLooping = false
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding?.centerCircularFrameLayout?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.nextExercise?.visibility = View.VISIBLE
        binding?.nextExerciseName?.visibility = View.VISIBLE
        binding?.exerciseViewId?.visibility = View.INVISIBLE
        binding?.exerciesFrameLayout?.visibility = View.INVISIBLE
        binding?.imageViewId?.visibility = View.INVISIBLE

        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        //speakOut("Relax")
        binding?.nextExerciseName?.text = exerciseList!![currentExercisePosition+1].getName()
        setRestProgressBar()
    }

    private fun setUpExerciseView(){

        binding?.centerCircularFrameLayout?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.nextExercise?.visibility = View.INVISIBLE
        binding?.nextExerciseName?.visibility = View.INVISIBLE
        binding?.exerciseViewId?.visibility = View.VISIBLE
        binding?.exerciesFrameLayout?.visibility = View.VISIBLE
        binding?.imageViewId?.visibility = View.VISIBLE

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        binding?.imageViewId?.setImageResource(exerciseList!![currentExercisePosition].getImage())

        binding?.exerciseViewId?.text = exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()
    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(restTimerDuration*1000,1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.textViewLinearLayout?.text = (10 - restProgress).toString()
            }
            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setUpExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar(){
        binding?.progressBarExercise?.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(exerciseTimerDuration*1000,1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.textViewLinearLayoutExercise?.text = (30 - exerciseProgress).toString()
            }
            override fun onFinish() {

                if(currentExercisePosition < exerciseList?.size!! - 1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestView()
                } else {
                    finish()
                    val intent = Intent(this@exerciseActivity2 , ResultActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }
    override fun onDestroy() {
        super.onDestroy()
        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        if(speakText != null){
            speakText?.stop()
            speakText?.shutdown()
        }

        if(mediaPlayer != null){
            mediaPlayer!!.stop()
        }

        binding = null
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = speakText!!.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TextToSpeach","Language not Supported!")
            } else{
                Log.e("TextToSpeech","Initialization Failed!")
            }
        }
    }

    private fun speakOut(text: String) {
        speakText?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setUpExerciseStatusRecyclerView() {
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }
}