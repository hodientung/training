package com.example.voicelockscreen.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.voicelockscreen.R
import kotlinx.android.synthetic.main.actitvity_onboard.*

class OnboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actitvity_onboard)
        initView()
    }

    private fun initView() {
        val viewPagerAdapter = OnboardViewPagerAdapter(this) {
            view_pager.currentItem = view_pager.currentItem + 1
        }
        view_pager.adapter = viewPagerAdapter

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (view_pager.currentItem == 0)
            super.onBackPressed()
        else
            view_pager.currentItem = view_pager.currentItem - 1
    }
}