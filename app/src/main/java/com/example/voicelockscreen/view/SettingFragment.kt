package com.example.voicelockscreen.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voicelockscreen.BuildConfig
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.clearValues
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isShowTime
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {

    private lateinit var mAdapter: RecyclerViewSetting

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()
    }

    private fun initAction() {
        mAdapter.onItemClicked = {
            when (it) {
                0 -> startActivity(Intent(Settings.ACTION_SECURITY_SETTINGS))
                1 -> {}
                2 -> {
                    //to do
                }
                3 -> shareApp()

                4 -> rateUs()

                5 -> loadWebView()

                6 -> AboutFragment().pushToScreen(activity as MainActivity)
            }
        }
        tvBackSetting.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun shareApp() {
        var shareMessage = "\nLet me recommend you this application\n\n"
        shareMessage =
            shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n"
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareMessage)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, null))
    }

    private fun rateUs() {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)
            )
        )
    }

    private fun initView() {
        rvSetting.layoutManager = LinearLayoutManager(context)
        mAdapter = RecyclerViewSetting(context)
        mAdapter.dataModelSetting = Util.getListItemSetting(requireContext())
        rvSetting.adapter = mAdapter
    }

    private fun loadWebView() {
        WebViewFragment().pushToScreen(activity as MainActivity)
    }
}