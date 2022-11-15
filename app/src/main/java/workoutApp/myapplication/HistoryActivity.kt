package workoutApp.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.LayoutDirection
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import workoutApp.myapplication.databinding.ActivityHistoryScreenBinding

class HistoryActivity : AppCompatActivity() {

    private var binding: ActivityHistoryScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolBarHistory)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "HISTORY"
        }
        binding?.toolBarHistory?.setNavigationOnClickListener{
            onBackPressed()
        }
        val dao = (application as WorkOutApp).db.historyDao()
        getAllDates(dao)
    }

    private fun getAllDates(historyDao: HistoryDao){
        lifecycleScope.launch{
            historyDao.fetchAllDates().collect { allCompletedDatesList ->
                if(allCompletedDatesList.isNotEmpty()){
                    binding?.tvNormalText?.visibility = View.VISIBLE
                    binding?.recyclerView?.visibility = View.VISIBLE
                    binding?.tvNoRecord?.visibility = View.GONE
                    binding?.recyclerView?.layoutManager =
                        LinearLayoutManager(this@HistoryActivity,LinearLayoutManager.VERTICAL,false)
                    val dates = ArrayList<String>()
                    for(D in allCompletedDatesList){
                        dates.add(D.date)
                    }
                    val historyAdapter = HistoryAdapter(dates)
                    binding?.recyclerView?.adapter = historyAdapter
                }   else{
                    binding?.tvNormalText?.visibility = View.GONE
                    binding?.recyclerView?.visibility = View.GONE
                    binding?.tvNoRecord?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}