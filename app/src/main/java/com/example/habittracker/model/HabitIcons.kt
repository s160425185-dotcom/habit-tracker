package com.example.habittracker.model

import androidx.annotation.DrawableRes
import com.example.habittracker.R

data class HabitIcon(val key: String, val label: String, @DrawableRes val resId: Int)

object HabitIcons {
    val all = listOf(
        HabitIcon("fitness", "Fitness", R.drawable.ic_fitness),
        HabitIcon("water", "Water", R.drawable.ic_water),
        HabitIcon("book", "Reading", R.drawable.ic_book),
        HabitIcon("meditation", "Meditation", R.drawable.ic_meditation),
        HabitIcon("sleep", "Sleep", R.drawable.ic_sleep),
        HabitIcon("walk", "Walking", R.drawable.ic_walk),
        HabitIcon("pray", "Prayer", R.drawable.ic_pray)
    )

    @DrawableRes
    fun resFor(key: String): Int =
        all.firstOrNull { it.key == key }?.resId ?: R.drawable.ic_fitness
}
