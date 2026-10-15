package com.example.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitRepository

data class FormErrors(
    val name: String? = null,
    val description: String? = null,
    val goal: String? = null,
    val unit: String? = null
) {
    val hasError: Boolean
        get() = name != null || description != null || goal != null || unit != null
}

class NewHabitViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = HabitRepository(application)

    private val _formErrors = MutableLiveData(FormErrors())
    val formErrors: LiveData<FormErrors> = _formErrors

    private val _saved = MutableLiveData(false)
    val saved: LiveData<Boolean> = _saved

    fun createHabit(
        name: String,
        description: String,
        goalText: String,
        unit: String,
        iconKey: String
    ) {
        val goal = goalText.trim().toIntOrNull()
        val errors = FormErrors(
            name = if (name.isBlank()) "Habit name is required" else null,
            description = if (description.isBlank()) "Description is required" else null,
            goal = when {
                goalText.isBlank() -> "Goal is required"
                goal == null || goal <= 0 -> "Goal must be a number greater than 0"
                else -> null
            },
            unit = if (unit.isBlank()) "Unit is required" else null
        )
        _formErrors.value = errors
        if (errors.hasError || goal == null) return

        repository.addHabit(
            Habit(
                id = System.currentTimeMillis(),
                name = name.trim(),
                description = description.trim(),
                goal = goal,
                unit = unit.trim(),
                iconKey = iconKey,
                progress = 0
            )
        )
        _saved.value = true
    }
}
