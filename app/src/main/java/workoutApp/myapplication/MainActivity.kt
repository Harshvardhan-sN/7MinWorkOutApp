package workoutApp.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import workoutApp.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.frameLayout?.setOnClickListener {
            val intent = Intent(this, exerciseActivity2::class.java)
            startActivity(intent)
        }

        binding?.bmiLayout?.setOnClickListener {
            val intent = Intent(this, bmiActivity::class.java )
            startActivity(intent)
        }

        binding?.historyLayout?.setOnClickListener {
            val intent = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}