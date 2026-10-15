package com.example.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitRepository

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = HabitRepository(application)

    private val _habits = MutableLiveData<List<Habit>>()
    val habits: LiveData<List<Habit>> = _habits

    fun loadHabits() {
        _habits.value = repository.getHabits()
    }

    fun increaseProgress(habitId: Long) {
        repository.changeProgress(habitId, +1)
        loadHabits()
    }

    fun decreaseProgress(habitId: Long) {
        repository.changeProgress(habitId, -1)
        loadHabits()
    }
}
