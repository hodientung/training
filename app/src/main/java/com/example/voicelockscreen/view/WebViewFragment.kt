package com.example.voicelockscreen.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.voicelockscreen.R
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadWebView()
    }

    private fun loadWebView() {
        contentWebView.apply {
            // Configure related browser settings
            this.settings.loadsImagesAutomatically = true
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            // Configure the client to use when opening URLs
            webViewClient = WebViewClient()
            // Load the initial URL
            loadUrl(Util.LINK_POLICY)
        }
    }

}