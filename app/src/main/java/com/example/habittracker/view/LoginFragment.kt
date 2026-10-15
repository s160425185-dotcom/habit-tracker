package com.example.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentLoginBinding
import com.example.habittracker.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.etUsername.text.toString().trim(),
                binding.etPassword.text.toString()
            )
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            binding.tvError.isVisible = message != null
            binding.tvError.text = message
        }

        viewModel.loginSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                viewModel.onLoginHandled()
                findNavController().navigate(R.id.action_login_to_dashboard)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
