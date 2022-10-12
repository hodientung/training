package com.example.voicelockscreen.view

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.voicelockscreen.R
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {
        val pm: PackageManager? = activity?.applicationContext?.packageManager
        val pkgName: String? = activity?.applicationContext?.packageName
        var pkgInfo: PackageInfo? = null
        try {
            pkgInfo = pkgName?.let { pm?.getPackageInfo(it, 0) }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        val ver = pkgInfo?.versionName
        versionApp.text = getString(R.string.ver) +" "+ver?.substring(0, 5)
        tvBackAbout.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}