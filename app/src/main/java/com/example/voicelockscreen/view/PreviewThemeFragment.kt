package com.example.voicelockscreen.view

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.engine.Resource
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
        dataTheme?.bg?.let { content_add_view.setBackgroundResource(it) }
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
        tvPreviewBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        val background1 = view1Win.background
        val background2 = view2Win.background
        val background3 = view3Win.background
        dataTheme?.bgFunction?.let {
            ContextCompat.getColor(
                requireContext(),
                it
            )
        }?.let {
            background1.setTint(it)
            background2.setTint(it)
            background3.setTint(it)
        }
        dataTheme?.colorVoice?.let {
            imV.setColorFilter(ContextCompat.getColor(requireContext(), it))
        }
        dataTheme?.imVoice?.let { imBackgroundVoice.setBackgroundResource(it) }
        dataTheme?.fontText?.let {
            tvSpeak.typeface = ResourcesCompat.getFont(requireContext(), it)
            tvForget.typeface = ResourcesCompat.getFont(requireContext(), it)
        }
        if (position == 7)
            dataTheme?.fontTextForget?.let {
                tvForget.typeface = ResourcesCompat.getFont(requireContext(), it)
            }
        dataTheme?.colorText?.let {
            tvSpeak.setTextColor(ContextCompat.getColor(requireContext(), it))
        }
        dataTheme?.colorText?.let {
            tvForget.setTextColor(ContextCompat.getColor(requireContext(), it))
        }
        dataTheme?.sizeText1?.let {
            tvSpeak.setTextSize(TypedValue.COMPLEX_UNIT_SP, it.toFloat())
        }
        dataTheme?.sizeText2?.let {
            tvForget.setTextSize(TypedValue.COMPLEX_UNIT_SP, it.toFloat())
        }
    }
}