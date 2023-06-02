package com.example.storyapp.ui.home.ui.post

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.storyapp.R
import com.example.storyapp.databinding.FragmentPostBinding
import com.example.storyapp.reduceFileSize
import com.example.storyapp.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.UUID

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var getFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val postViewModel =
            ViewModelProvider(this).get(PostViewModel::class.java)

        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textView
        postViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        postViewModel.loading.observe(viewLifecycleOwner){
            binding.progressPost.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }

        postViewModel.success.observe(viewLifecycleOwner){
            if (it) {
                Toast.makeText(requireContext(), R.string.success_post, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.navigation_home)
            }
        }

        binding.btnGallery.setOnClickListener {
            startGallery()
        }

        binding.btnCamera.setOnClickListener {
            startCamera()
        }

        binding.btnPost.setOnClickListener {
            if (getFile != null && binding.edAddDescription.text.toString() != ""){
                val file = reduceFileSize(getFile as File)

                val description = binding.edAddDescription.text.toString().toRequestBody("text/plain".toMediaType())
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    file.name,
                    requestImageFile
                )

                postViewModel.post(imageMultipart, description)
            }else{
                Toast.makeText(requireActivity(), R.string.chooser.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, requireActivity())
                binding.ivPostPreview.setImageURI(uri)

                //set file to variable
                getFile = myFile
            }
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val imageBitmap = it.data?.extras?.get("data") as Bitmap
            binding.ivPostPreview.setImageBitmap(imageBitmap)

            //convert
            getFile = bitmapToFile(imageBitmap)
        }
    }

    private fun startGallery(){
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, R.string.chooser.toString())
        launcherIntentGallery.launch(chooser)
    }

    private fun bitmapToFile(image: Bitmap): File {
        val wrapper = ContextWrapper(requireContext())
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file,"${UUID.randomUUID()}.jpg")
        val stream: OutputStream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.JPEG,25,stream)
        stream.flush()
        stream.close()

        return file
    }

    private fun startCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        launcherIntentCamera.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}