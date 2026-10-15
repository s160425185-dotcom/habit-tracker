package com.example.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.habittracker.databinding.FragmentNewHabitBinding
import com.example.habittracker.model.HabitIcons
import com.example.habittracker.viewmodel.NewHabitViewModel

class NewHabitFragment : Fragment() {

    private var _binding: FragmentNewHabitBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHabitViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Spinner (combo box) pilihan icon
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            HabitIcons.all.map { it.label }
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerIcon.adapter = spinnerAdapter

        binding.btnCreate.setOnClickListener {
            val selectedIcon = HabitIcons.all[binding.spinnerIcon.selectedItemPosition]
            viewModel.createHabit(
                name = binding.etName.text.toString(),
                description = binding.etDescription.text.toString(),
                goalText = binding.etGoal.text.toString(),
                unit = binding.etUnit.text.toString(),
                iconKey = selectedIcon.key
            )
        }

        viewModel.formErrors.observe(viewLifecycleOwner) { errors ->
            binding.tilName.error = errors.name
            binding.tilDescription.error = errors.description
            binding.tilGoal.error = errors.goal
            binding.tilUnit.error = errors.unit
        }

        viewModel.saved.observe(viewLifecycleOwner) { saved ->
            if (saved) {
                Toast.makeText(requireContext(), "Habit created", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
