package com.example.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentDashboardBinding
import com.example.habittracker.viewmodel.DashboardViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HabitAdapter(
            onPlus = { viewModel.increaseProgress(it.id) },
            onMinus = { viewModel.decreaseProgress(it.id) }
        )
        binding.recyclerHabits.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerHabits.adapter = adapter

        viewModel.habits.observe(viewLifecycleOwner) { habits ->
            adapter.submitList(habits)
            binding.tvEmpty.isVisible = habits.isEmpty()
        }

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_newHabit)
        }

        // Dipanggil setiap view dibuat ulang, termasuk saat kembali dari halaman Create New Habit
        viewModel.loadHabits()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
