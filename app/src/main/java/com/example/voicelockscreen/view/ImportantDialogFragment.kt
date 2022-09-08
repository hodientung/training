package com.example.voicelockscreen.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.input
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.custom_dialog_important.*

class ImportantDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_dialog_important, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {
        val prefs = context?.let {
            PreferenceHelper.customPreference(
                it,
                Util.CUSTOM_PREF_NAME
            )
        }
        tv2.text = getString(R.string.report_input, prefs?.input)
        btnSubmitImportant.setOnClickListener {
            Toast.makeText(requireContext(), "Successful Set Voice password!!!", Toast.LENGTH_LONG)
                .show()
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setGravity(Gravity.BOTTOM)
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}