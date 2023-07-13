package workoutApp.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import workoutApp.myapplication.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class bmiActivity : AppCompatActivity() {

    private var binding: ActivityBmiBinding? = null
    companion object{
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }
    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.bmiToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Calculate BMI"
        binding?.bmiToolBar?.setNavigationOnClickListener{
            onBackPressed()
        }

        metricViewVisible()

        binding?.radioGroupId?.setOnCheckedChangeListener { _, checkId -> Int
            if(checkId == R.id.metricUnits){
                metricViewVisible()
            }   else{
                usViewVisible()
            }
        }

        binding?.calculateBtn?.setOnClickListener {
            calculateUnits()
        }

    }
    private fun calculateUnits() {
        if(currentVisibleView == METRIC_UNITS_VIEW){
            if(validateValuesForMetric()){
                val h: Float = binding?.textHeightCM?.text.toString().toFloat() / 100
                val w: Float = binding?.textWeightKG?.text.toString().toFloat()
                val bmi = w / (h*h)
                displayValues(bmi)
            }   else{
                Toast.makeText(this@bmiActivity,"Please Enter Some Valid Values"
                    ,Toast.LENGTH_SHORT).show()
            }
        }
        else{
            if (validateValuesForUS()) {
                val w: Float = binding?.textWeightPounds?.text.toString().toFloat()
                val h: Float = binding?.Feet?.text.toString().toFloat()*12 +
                        binding?.Inch?.text.toString().toFloat()
                val bmi = (w / (h*h))*703
                displayValues(bmi)
            }
            else{
                Toast.makeText(this@bmiActivity,"Please Enter Some Valid Values"
                    ,Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun metricViewVisible(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.MetricMainLayout?.visibility = View.VISIBLE
        binding?.UsMainLayout?.visibility = View.INVISIBLE
        binding?.textHeightCM?.text!!.clear()
        binding?.textWeightKG?.text!!.clear()
        binding?.calcOutputLinear?.visibility = View.INVISIBLE
    }
    private fun usViewVisible(){
        currentVisibleView = US_UNITS_VIEW
        binding?.MetricMainLayout?.visibility = View.INVISIBLE
        binding?.UsMainLayout?.visibility = View.VISIBLE
        binding?.textWeightPounds?.text!!.clear()
        binding?.Feet?.text!!.clear()
        binding?.Inch?.text!!.clear()
        binding?.calcOutputLinear?.visibility = View.INVISIBLE
    }

    private fun displayValues(bmi: Float){
        binding?.calcOutputLinear?.visibility = View.VISIBLE

        val type : String
        val description: String
        if (bmi<18.5){
            type = "UnderWeight"
            description = "Focus on building muscle and gaining weight in a healthy way. Incorporate strength training exercises and ensure you're consuming enough calories."
        }
        else if(bmi>=18.5 && bmi<25){
            type = "Healthy"
            description = "Stay consistent with exercise and a balanced diet. Try different workout styles and listen to your body's needs for rest and recovery."

        }
        else if(bmi>=25 && bmi<30){
            type = "OverWeight"
            description = "Burn calories with cardiovascular exercises and make gradual changes to your eating habits. Seek professional guidance for a sustainable weight loss plan."
        }
        else{
            type = "Obesity"
            description = "Consult a healthcare professional for personalized advice. Adopt a balanced diet and regular physical activity. Set realistic goals and seek support for motivation."
        }
        val bmiVal = BigDecimal(bmi.toDouble())
            .setScale(2,RoundingMode.HALF_EVEN).toString()
        binding?.yourBmiValue?.text = bmiVal
        binding?.yourBmiType?.text = type
        binding?.yourBmiDescription?.text = description
    }

    private fun validateValuesForMetric(): Boolean{
        var isValid = true
        if( binding?.textHeightCM?.text.toString().isEmpty()) {
            isValid = false
        }   else if(binding?.textWeightKG?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun validateValuesForUS(): Boolean{
        var isValid = true
        when {
            binding?.Inch?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.textWeightPounds?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.Feet?.text.toString().isEmpty() -> {
                isValid = false
            }
        }
        return isValid
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}