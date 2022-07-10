package com.example.shoestoreinventory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shoestoreinventory.MainActivityViewModel
import com.example.shoestoreinventory.databinding.FragmentInstructionsShoeListBinding
import com.example.shoestoreinventory.models.PrefManager


class InstructionsShoeListFragment : Fragment() {
    lateinit var binding: FragmentInstructionsShoeListBinding
    private val viewModel: MainActivityViewModel by activityViewModels()
    lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInstructionsShoeListBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        prefManager = PrefManager(requireContext())
        observe()
        return binding.root
    }

    private fun observe() {
        viewModel.instructionsShoeListNextClicked.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    InstructionsShoeListFragmentDirections.actionInstructionsFragmentToInstructionsShoeDetailFragment()
                )
                viewModel.onInstructionsShoeListNextClickSuccess()
            }
        }
    }


}