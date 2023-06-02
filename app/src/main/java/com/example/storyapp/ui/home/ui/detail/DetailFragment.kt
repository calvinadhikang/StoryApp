package com.example.storyapp.ui.home.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.storyapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    companion object;

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val id = requireArguments().getString("storyId")
        viewModel.story.observe(viewLifecycleOwner) {
            binding.tvDetailName.text = it.name
            binding.tvDetailDescription.text = it.description

            Glide
                .with(requireActivity())
                .load(it.photoUrl)
                .into(binding.ivDetailPhoto)
        }

        viewModel.fetchDetail(id!!)

        return root
    }
}