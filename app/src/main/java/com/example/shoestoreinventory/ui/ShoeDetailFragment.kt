package com.example.shoestoreinventory.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaRecorder
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shoestoreinventory.R
import com.example.shoestoreinventory.MainActivityViewModel
import com.example.shoestoreinventory.databinding.FragmentShoeDetailBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class ShoeDetailFragment : Fragment() {
    lateinit var binding: FragmentShoeDetailBinding
    private val viewModel: MainActivityViewModel by activityViewModels()
    val REQUEST_CAMERA_CODE = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail, container, false)
        binding.viewmodel = viewModel
        binding.shoe = viewModel.shoe
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel.onShoeDetailChooseFromSavedClick(binding.ivShoeImage)
        viewModel.onShoeImageChangedSuccess(binding.ivShoeImage)
        binding.tietShoeSize.setAdapter(
            ArrayAdapter<String>(
                requireContext(),
                R.layout.shoe_size_dropdowm_item,
                viewModel.shoe.size_list
            )
        )
        observe()
        return binding.root

    }

    private fun observe() {
        viewModel.shoeDetailSaveClicked.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
                viewModel.onShoeDetailSaveClickSuccess()
            }
        }
        viewModel.shoeDetailCancelClicked.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
                viewModel.onShoeDetailCancelClickSuccess()
            }
        }
        viewModel.shoeDetailSaveErrorRaise.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val dialogBuilder = AlertDialog.Builder(context)
                    .setTitle("Missing Information")
                    .setNegativeButton("Ok") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setCancelable(false)
                    .setMessage(it)
                val dialog = dialogBuilder.create()
                dialog.show()
                viewModel.onShoeDetailErrorRaiseSuccess()
            }
        }
        viewModel.shoeDetailImageChanged.observe(viewLifecycleOwner) {
            if (it) {

                when (viewModel.shoeImageIndex.value) {
                    0 -> binding.ivShoeImage.setImageResource(R.drawable.shoe_img_1)
                    1 -> binding.ivShoeImage.setImageResource(R.drawable.shoe_img_2)
                    2 -> binding.ivShoeImage.setImageResource(R.drawable.shoe_img_3)
                    3 -> binding.ivShoeImage.setImageResource(R.drawable.shoe_img_4)
                    4 -> binding.ivShoeImage.setImageResource(R.drawable.shoe_img_5)
                }
                viewModel.onShoeImageChangedSuccess(binding.ivShoeImage)
            }
        }
        viewModel.shoeDetailShoeImageTaken.observe(viewLifecycleOwner) {
            if (it) {
                takePhoto()
                viewModel.onShoeDetailTakeShoeImageClickSuccess()
            }
        }
    }

    private fun takePhoto() {
        //request camera permission
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            GlobalScope.async {
                startActivityForResult(intent, MediaRecorder.VideoSource.CAMERA)
                onActivityResult(Activity.RESULT_OK, MediaRecorder.VideoSource.CAMERA, intent)
            }
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_CODE
            )
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearShoe()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MediaRecorder.VideoSource.CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                /** photo taken
                 * get the image
                 * and set it to the image view
                 * then save the image to the view model
                 * */
                Toast.makeText(context, "Photo Taken", Toast.LENGTH_LONG).show()
                binding.ivShoeImage.setImageBitmap((data?.extras?.get("data") as Bitmap))
                viewModel.onShoeImageChangedSuccess(binding.ivShoeImage)

            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            REQUEST_CAMERA_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto()
                } else {
                    Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}