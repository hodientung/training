package com.example.voicelockscreen.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isRate
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.fragment_rate_us.*


class RateUsFragment : DialogFragment() {
    private var message = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rate_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {
        val prefs =
            context?.let {
                PreferenceHelper.customPreference(
                    it,
                    Util.RATE_ME_TUNG
                )
            }
        rateBar.setOnRatingBarChangeListener { ratingBar, _, _ ->
            message = ratingBar.rating.toInt()
            if (message in 1..5) tvThankYou.text = "Thank you!"
        }
        tvThankYou.setOnClickListener {
            prefs?.isRate = true
            when (message) {
                in 1..3 -> sendEmail(message)
                in 4..5 -> rateUsOnGooglePlay()
                else -> {
                    Toast.makeText(
                        requireContext(),
                        "You don't rate me, try again, ",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    prefs?.isRate = false
                }
            }
        }
    }

    private fun rateUsOnGooglePlay() {
        dialog?.dismiss()
        val uri = Uri.parse("market://details?id=" + activity?.packageName)
        val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
        myAppLinkToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(myAppLinkToMarket)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), " unable to find market app", Toast.LENGTH_LONG).show()
        }
    }

    private fun sendEmail(message: Int) {
        dialog?.dismiss()
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, "")
            putExtra(Intent.EXTRA_SUBJECT, "Voice Lock Screen")
            putExtra(Intent.EXTRA_TEXT, "Rate: $message \n Content:\n")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }


    override fun onStart() {
        super.onStart()
        val percent = 0.85f
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setGravity(Gravity.CENTER)
//        dialog?.window?.setLayout(
//            WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.WRAP_CONTENT
//        )
    }
}