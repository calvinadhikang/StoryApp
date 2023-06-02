package com.example.storyapp.ui.home.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.UserPreference
import com.example.storyapp.databinding.FragmentProfileBinding
import com.example.storyapp.ui.main.MainActivity
import com.example.storyapp.ui.main.MainViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.actionLogout.setOnClickListener {
            val pref = UserPreference(requireActivity())
            pref.clearUser()

            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.tvProfileId.text = "ID : ${MainViewModel.LOGIN_ID}"
        binding.tvProfileName.text = "Name : ${MainViewModel.LOGIN_NAME}"
        binding.tvProfileToken.text = "Token : ${MainViewModel.API_KEY}"

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}