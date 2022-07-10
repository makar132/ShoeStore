package com.example.shoestoreinventory.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shoestoreinventory.R
import com.example.shoestoreinventory.MainActivityViewModel
import com.example.shoestoreinventory.databinding.FragmentShoeListBinding
import com.example.shoestoreinventory.databinding.ShoeListItemBinding
import com.example.shoestoreinventory.models.PrefManager
import com.example.shoestoreinventory.models.Shoe

class ShoeListFragment : Fragment() {
    lateinit var binding: FragmentShoeListBinding
    private val viewModel: MainActivityViewModel by activityViewModels()
    lateinit var prefManager: PrefManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_list, container, false
        )
        prefManager = PrefManager(requireContext())
        binding.viewmodel = viewModel
        observe()
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun observe() {
        viewModel.shoeListAddShoeClicked.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    findNavController().navigate(
                        ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment()
                    )
                    viewModel.onShoeListAddShoeClickSuccess()
                }
            }
        }
        viewModel.shoesList.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isEmpty()) {
                    Toast.makeText(context, "No shoes in the store", Toast.LENGTH_LONG).show()
                }
                addShoe(it)
            }
        }
    }

    private fun addShoe(mutableList: MutableList<Shoe>) {
        for (shoe in mutableList) {
            val shoeBinding: ShoeListItemBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.shoe_list_item,
                binding.shoeList,
                false
            )
            shoeBinding.ivShoeImage.setImageBitmap(shoe.image)
            shoeBinding.tvShoeName.text = shoe.name
            shoeBinding.tvShoeCompany.text = shoe.company
            shoeBinding.tvShoeSize.text = shoe.size
            shoeBinding.tvShoeDescription.text = shoe.description
            binding.shoeList.addView(shoeBinding.root)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                prefManager.logout()
                findNavController().navigate(R.id.action_shoeListFragment_to_loginFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }




}