package com.example.shoestoreinventory

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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shoestoreinventory.databinding.FragmentShoeDetailBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class ShoeDetailFragment : Fragment() {
    lateinit var binding: FragmentShoeDetailBinding
    private val viewModel: SharedViewModel by activityViewModels()
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
        viewModel.onShoeImageChanged(binding.ivShoeImage)
        viewModel.onShoeImageChangedComplete(binding.ivShoeImage)
        observe()
        return binding.root

    }

    private fun observe() {
        viewModel.eventSaveShoePress.observe(viewLifecycleOwner) {
            if (it) {
                cancel()
                viewModel.onSaveShoePressComplete()
            }
        }
        viewModel.eventCancelPress.observe(viewLifecycleOwner) {
            if (it) {
                cancel()
                viewModel.onCancelPressComplete()
            }
        }
        viewModel.eventSaveShoeError.observe(viewLifecycleOwner) {
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
                viewModel.clearError()
            }
        }
        viewModel.eventShoeImageChange.observe(viewLifecycleOwner) {
            if (it) {

                when (viewModel.shoeImageIndex.value) {
                    0 -> binding.ivShoeImage.setImageResource(R.drawable.shoe_img_1)
                    1 -> binding.ivShoeImage.setImageResource(R.drawable.shoe_img_2)
                    2 -> binding.ivShoeImage.setImageResource(R.drawable.shoe_img_3)
                    3 -> binding.ivShoeImage.setImageResource(R.drawable.shoe_img_4)
                    4 -> binding.ivShoeImage.setImageResource(R.drawable.shoe_img_5)
                }
                viewModel.onShoeImageChangedComplete(binding.ivShoeImage)
            }
        }
        viewModel.eventTakeShoeImage.observe(viewLifecycleOwner) {
            if (it) {
                takePhoto()
                viewModel.onTakeShoeImageComplete()
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

    private fun cancel() {
        findNavController().popBackStack()
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
                viewModel.onShoeImageChangedComplete(binding.ivShoeImage)

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