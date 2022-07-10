package com.example.shoestoreinventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shoestoreinventory.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {
    lateinit var binding: FragmentWelcomeBinding
    private val viewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)
        binding.viewmodel = viewModel
        observe()
        return binding.root
    }

    private fun observe() {
        viewModel.eventWelcome.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToInstructionsFragment())
                viewModel.onWelcomeComplete()
            }

        }
    }


}