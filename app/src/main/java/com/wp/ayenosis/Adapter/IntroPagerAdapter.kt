package com.wp.ayenosis.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wp.ayenosis.fragment.onboarding.FirstIntroFragment
import com.wp.ayenosis.fragment.onboarding.SecondIntroFragment
import com.wp.ayenosis.fragment.onboarding.ThirdIntroFragment

class IntroPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity)  {
    private val items = 3

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FirstIntroFragment()
            1 -> fragment = SecondIntroFragment()
            2 -> fragment = ThirdIntroFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int = items
}