package com.example.voicelockscreen.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isFistOnboard
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.actitvity_onboard.*

class OnboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actitvity_onboard)
        initView()
        initAction()
    }

    private fun initAction() {
        indicator_view.setViewPager(view_pager)
        indicator_view.animatePageSelected(1)
        tvSkip.setOnClickListener {
            val prefs = PreferenceHelper.customPreference(this, Util.ON_BOARDING)
            prefs.isFistOnboard = true
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) tvSkip.visibility = View.GONE
                else tvSkip.visibility = View.VISIBLE
            }
        })
    }

    private fun initView() {
        val viewPagerAdapter = OnboardViewPagerAdapter(this) {
            view_pager.setPageTransformer(ZoomOutPageTransformer())
            view_pager.currentItem = view_pager.currentItem + 1
        }
        view_pager.setPageTransformer(ZoomOutPageTransformer())
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