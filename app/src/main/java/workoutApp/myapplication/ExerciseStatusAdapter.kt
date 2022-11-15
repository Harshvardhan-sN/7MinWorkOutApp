package workoutApp.myapplication

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: Model = items[position]
        holder.tvItem.text = model.getId().toString()
        when{
            model.getIsSelected() -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.green_color_selected_bg)
                holder.tvItem.setTextColor(Color.parseColor("#14213d"))
            }
            model.getIsCompleted() -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_cicular_color_accent_background)
                holder.tvItem.setTextColor(Color.parseColor("#e5e5e5"))
            }
            else -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_color_red_bg)
                holder.tvItem.setTextColor(Color.parseColor("#f8f9fa"))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}