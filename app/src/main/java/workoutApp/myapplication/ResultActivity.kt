package workoutApp.myapplication

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import workoutApp.myapplication.databinding.ActivityResultBinding
import java.util.*

class ResultActivity : AppCompatActivity() {

    private var binding: ActivityResultBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercies)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercies?.setNavigationOnClickListener{
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener {
            finish()
        }
        val dao = (application as WorkOutApp).db.historyDao()
        addData(dao)
    }

    private fun addData(historyDao: HistoryDao){
        val c = Calendar.getInstance()
        val dateTime = c.time
        Log.e("Date: ","" + dateTime)
        val sdf = java.text.SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        Log.e("Formatted Date : ", "" + date)

        lifecycleScope.launch{
            historyDao.insert(HistoryEntity(date))
            Log.e(
                "Date : $date",
                "Added..."
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}