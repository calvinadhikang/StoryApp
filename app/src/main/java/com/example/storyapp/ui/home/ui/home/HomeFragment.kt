package com.example.storyapp.ui.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.R
import com.example.storyapp.adapter.StoryPagerAdapter
import com.example.storyapp.databinding.FragmentHomeBinding
import com.example.storyapp.response.ListStoryItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        //initiate adapter
        val layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        binding.rvStories.layoutManager = layoutManager
        showStoryData()

        return root
    }

    private fun showStoryData(){
        val adapter = StoryPagerAdapter { data: ListStoryItem ->
            val controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_home)
            val bundle = Bundle()
            bundle.putString("storyId", data.id)
            controller.navigate(R.id.navigation_detail, bundle)
        }
        binding.rvStories.adapter = adapter

        homeViewModel.story().observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}