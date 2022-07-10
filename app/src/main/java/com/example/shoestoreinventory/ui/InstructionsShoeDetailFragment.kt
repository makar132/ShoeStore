package com.example.shoestoreinventory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shoestoreinventory.R
import com.example.shoestoreinventory.MainActivityViewModel
import com.example.shoestoreinventory.databinding.FragmentInstructionsShoeDetailBinding
import com.example.shoestoreinventory.models.PrefManager

class InstructionsShoeDetailFragment : Fragment() {
    lateinit var binding: FragmentInstructionsShoeDetailBinding
    private val viewModel: MainActivityViewModel by activityViewModels()
    lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_instructions_shoe_detail,
            container,
            false
        )
        prefManager= PrefManager(requireContext())
        binding.viewmodel=viewModel
        observe()
        return binding.root
    }

    private fun observe() {
        viewModel.instructionsSoeDetailGetStartedClicked.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    InstructionsShoeDetailFragmentDirections.actionInstructionsShoeDetailFragmentToShoeListFragment()
                )
                prefManager.setonboarding(true)
                viewModel.onInstructionsShoeDetailGetStartedClickSuccess()
            }

        }
    }

}