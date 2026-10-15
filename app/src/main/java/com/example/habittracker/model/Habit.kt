package com.example.habittracker.model

data class Habit(
    val id: Long,
    val name: String,
    val description: String,
    val goal: Int,
    val unit: String,
    val iconKey: String,
    val progress: Int = 0
) {
    val isCompleted: Boolean
        get() = progress >= goal
}
