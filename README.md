# Habit Tracker (ANMP Project 1)

Aplikasi Android (Kotlin, XML Views) dengan arsitektur **MVVM**, **Navigation Component**,
dan penyimpanan data **SharedPreferences**.

## Cara menjalankan
1. Buka Android Studio -> **File > Open** -> pilih folder `HabitTracker` (yang berisi `settings.gradle.kts`).
2. Tunggu **Gradle Sync** selesai (butuh internet untuk download dependency pertama kali).
   Jika muncul tawaran "Upgrade Gradle / AGP", boleh diabaikan atau disetujui.
3. Pilih emulator / HP (minSdk 24), lalu klik **Run**.
4. Login: username `student`, password `123`.

## Struktur
- `model/` : Habit, HabitIcons, HabitRepository (SharedPreferences + JSON)
- `viewmodel/` : LoginViewModel, DashboardViewModel, NewHabitViewModel
- `view/` : MainActivity, LoginFragment, DashboardFragment, NewHabitFragment, HabitAdapter
- `res/navigation/nav_graph.xml` : graph Navigation
