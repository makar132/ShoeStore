package com.example.shoestoreinventory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shoestoreinventory.databinding.FragmentInstructionsBinding
import com.example.shoestoreinventory.models.PrefManager


class InstructionsFragment : Fragment() {
    lateinit var binding: FragmentInstructionsBinding
    private val viewModel: SharedViewModel by activityViewModels()
    lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_instructions,container,false)
        binding.viewmodel=viewModel
        prefManager=PrefManager(requireContext())
        observe()
        return binding.root
    }

    private fun observe() {
        viewModel.eventInstructions.observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(
                    InstructionsFragmentDirections.actionInstructionsFragmentToShoeListFragment()
                )
                prefManager.setonboarding(true)
                viewModel.onInstructionsComplete()
            }
        }
    }


}