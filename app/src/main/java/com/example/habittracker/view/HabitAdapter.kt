package com.example.habittracker.view

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.R
import com.example.habittracker.databinding.ItemHabitBinding
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitIcons

class HabitAdapter(
    private val onPlus: (Habit) -> Unit,
    private val onMinus: (Habit) -> Unit
) : ListAdapter<Habit, HabitAdapter.HabitViewHolder>(DiffCallback) {

    inner class HabitViewHolder(val binding: ItemHabitBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = getItem(position)
        val ctx = holder.itemView.context
        val density = ctx.resources.displayMetrics.density
        val green = ContextCompat.getColor(ctx, R.color.green)
        val primary = ContextCompat.getColor(ctx, R.color.primary)

        with(holder.binding) {
            ivIcon.setImageResource(HabitIcons.resFor(habit.iconKey))
            tvName.text = habit.name
            tvDescription.text = habit.description
            tvProgress.text = "${habit.progress} / ${habit.goal} ${habit.unit}"

            progressBar.max = habit.goal
            progressBar.setProgressCompat(habit.progress, true)

            if (habit.isCompleted) {
                ivCheck.visibility = android.view.View.VISIBLE
                tvStatus.text = "Completed"
                tvStatus.setTextColor(ContextCompat.getColor(ctx, R.color.white))
                tvStatus.backgroundTintList = ColorStateList.valueOf(green)
                progressBar.setIndicatorColor(green)
                cardHabit.strokeColor = green
                cardHabit.strokeWidth = (2 * density).toInt()
            } else {
                ivCheck.visibility = android.view.View.GONE
                tvStatus.text = "In Progress"
                tvStatus.setTextColor(0xFF424242.toInt())
                tvStatus.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.gray_chip))
                progressBar.setIndicatorColor(primary)
                cardHabit.strokeWidth = 0
            }

            // Tombol + nonaktif jika sudah selesai, tombol - nonaktif jika progress = 0
            btnPlus.isEnabled = !habit.isCompleted
            btnPlus.alpha = if (habit.isCompleted) 0.4f else 1f
            btnMinus.isEnabled = habit.progress > 0
            btnMinus.alpha = if (habit.progress > 0) 1f else 0.4f

            btnPlus.setOnClickListener { onPlus(habit) }
            btnMinus.setOnClickListener { onMinus(habit) }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Habit, newItem: Habit) = oldItem == newItem
    }
}
