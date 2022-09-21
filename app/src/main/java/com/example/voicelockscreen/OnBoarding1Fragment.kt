package com.example.voicelockscreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import com.example.voicelockscreen.utils.Util.Companion.pushToScreenOfOnBoard
import com.example.voicelockscreen.view.OnBoarding2Fragment
import com.example.voicelockscreen.view.OnBoarding3Fragment
import com.example.voicelockscreen.view.OnboardActivity
import kotlinx.android.synthetic.main.actitvity_onboard.*
import kotlinx.android.synthetic.main.fragment_on_boarding1.*


class OnBoarding1Fragment(private val onClose: () -> Unit) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {
        btn1.setOnClickListener {
            onClose.invoke()
        }
    }

}