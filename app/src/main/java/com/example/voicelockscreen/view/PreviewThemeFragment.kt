package com.example.voicelockscreen.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelTheme
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themeCode
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themePinButton
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.fragment_preview_theme.*


class PreviewThemeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preview_theme, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {
        val dataTheme = arguments?.getSerializable("theme") as? DataModelTheme
        val position = arguments?.getInt("position")
        dataTheme?.colorTheme?.let { imTheme.setBackgroundResource(it) }
        btnApply.setOnClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.successfully_set_theme),
                Toast.LENGTH_LONG
            ).show()
            //save setting theme into share preference
            val prefs =
                context?.let { it1 ->
                    PreferenceHelper.customPreference(
                        it1,
                        Util.THEME_SETTING
                    )
                }
            prefs?.themeCode = position ?: 0
            prefs?.themePinButton = position ?: 0

        }
        tvBackPreview.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}