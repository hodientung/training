package com.example.voicelockscreen.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.voicelockscreen.R
import com.example.voicelockscreen.utils.Util.Companion.pushToScreenOfOnBoard
import kotlinx.android.synthetic.main.actitvity_onboard.*
import kotlinx.android.synthetic.main.fragment_on_boarding1.*
import kotlinx.android.synthetic.main.fragment_on_boarding2.*

class OnBoarding2Fragment(private val onClose: () -> Unit) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding2, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {
        imNext2.setOnClickListener {
            onClose.invoke()
        }
    }

}