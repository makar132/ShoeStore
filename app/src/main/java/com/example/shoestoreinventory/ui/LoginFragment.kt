package com.example.shoestoreinventory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shoestoreinventory.MainActivityViewModel
import com.example.shoestoreinventory.databinding.FragmentLoginBinding
import com.example.shoestoreinventory.models.PrefManager


class LoginFragment : Fragment() {
    lateinit var prefManager: PrefManager
    lateinit var binding: FragmentLoginBinding
    private val viewModel: MainActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        prefManager = PrefManager(requireContext())
        if (prefManager.Logedin() == true) {
            if (prefManager.onboarding() == true) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToShoeListFragment())
            } else {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
            }
        }
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        observe()
        return binding.root
    }

    private fun observe() {
        viewModel.loginClicked.observe(viewLifecycleOwner) {
            if (it) {
                prefManager.setLogin(true)
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
                viewModel.onLoginClickSuccess()
            }
        }
        viewModel.registerClicked.observe(viewLifecycleOwner) {
            if (it) {
                prefManager.setLogin(true)
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
                viewModel.onRegisterClickSuccess()
            }
        }
    }


}