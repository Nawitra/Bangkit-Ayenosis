package com.wp.ayenosis.fragment.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wp.ayenosis.activity.LoginActivity
import com.wp.ayenosis.activity.SignUpActivity
import com.wp.ayenosis.databinding.FragmentIntro3Binding

class ThirdIntroFragment : Fragment() {
    private lateinit var binding: FragmentIntro3Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntro3Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button3.setOnClickListener {
            val mIntent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(mIntent)
        }
    }
}