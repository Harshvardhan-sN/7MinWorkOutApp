package workoutApp.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import workoutApp.myapplication.databinding.ActivityBmiBinding
import workoutApp.myapplication.databinding.ItemRowsBinding

class HistoryAdapter(
    private val items: ArrayList<String>):
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRowsBinding.inflate(LayoutInflater.from
                (parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: String = items.get(position)
        holder.tvPosition.text = (position + 1).toString()
        holder.tvItem.text = date

        if(position%2==0){
            holder.llHistoryItemName.setBackgroundColor(Color.parseColor("#f1c0e8"))
        }   else{
            holder.llHistoryItemName.setBackgroundColor(Color.parseColor("#cfbaf0"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(binding: ItemRowsBinding):
        RecyclerView.ViewHolder(binding.root){
        val llHistoryItemName = binding.itemLinearLayout
        val tvItem = binding.tvItem
        val tvPosition = binding.tvPosition
    }
}