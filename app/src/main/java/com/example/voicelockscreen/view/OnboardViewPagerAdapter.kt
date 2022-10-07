package com.example.voicelockscreen.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.voicelockscreen.OnBoarding1Fragment

class OnboardViewPagerAdapter(
    fragmentActivity: FragmentActivity, private val onClose: () -> Unit
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> OnBoarding1Fragment(onClose)
        1 -> OnBoarding2Fragment(onClose)
        2 -> OnBoarding3Fragment()
        else -> OnBoarding1Fragment(onClose)
    }
}