package workoutApp.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import workoutApp.myapplication.databinding.ItemExersiceStatusBinding

class ExerciseStatusAdapter(val items: ArrayList<Model>):
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

        class ViewHolder(binding: ItemExersiceStatusBinding):
            RecyclerView.ViewHolder(binding.root) {
                val tvItem = binding.tvItemStatusId
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExersiceStatusBinding.inflate
            (LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: Model = items[position]
        holder.tvItem.text = model.getId().toString()
        when{
            model.getIsSelected() -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.ongoing_exercise_color_yellow)
                holder.tvItem.setTextColor(Color.parseColor("#000000"))
            }
            model.getIsCompleted() -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.finished_exercise_color_green)
                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.upcoming_exercise_red_color)
                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}