package com.example.habittracker.model

import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Penyimpanan data habit menggunakan SharedPreferences (data disimpan sebagai JSON string).
 */
class HabitRepository(context: Context) {

    private val prefs = context.applicationContext
        .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getHabits(): List<Habit> {
        val raw = prefs.getString(KEY_HABITS, null) ?: return emptyList()
        return try {
            val array = JSONArray(raw)
            (0 until array.length()).map { i ->
                val o = array.getJSONObject(i)
                Habit(
                    id = o.getLong("id"),
                    name = o.getString("name"),
                    description = o.getString("description"),
                    goal = o.getInt("goal"),
                    unit = o.getString("unit"),
                    iconKey = o.getString("iconKey"),
                    progress = o.getInt("progress")
                )
            }
        } catch (e: JSONException) {
            emptyList()
        }
    }

    fun addHabit(habit: Habit) {
        save(getHabits() + habit)
    }

    fun changeProgress(id: Long, delta: Int) {
        val updated = getHabits().map {
            if (it.id == id) it.copy(progress = (it.progress + delta).coerceIn(0, it.goal)) else it
        }
        save(updated)
    }

    private fun save(habits: List<Habit>) {
        val array = JSONArray()
        habits.forEach { h ->
            array.put(
                JSONObject().apply {
                    put("id", h.id)
                    put("name", h.name)
                    put("description", h.description)
                    put("goal", h.goal)
                    put("unit", h.unit)
                    put("iconKey", h.iconKey)
                    put("progress", h.progress)
                }
            )
        }
        prefs.edit().putString(KEY_HABITS, array.toString()).apply()
    }

    companion object {
        private const val PREFS_NAME = "habit_tracker_prefs"
        private const val KEY_HABITS = "habits"
    }
}
