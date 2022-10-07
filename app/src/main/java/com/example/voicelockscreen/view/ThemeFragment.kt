package com.example.voicelockscreen.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelTheme
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themeCode
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themePinButton
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import kotlinx.android.synthetic.main.fragment_theme.*
import java.util.ArrayList


class ThemeFragment : Fragment() {
    private lateinit var adapterTheme: RecyclerViewTheme

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_theme, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()

    }

    private fun initAction() {
        val bundle = Bundle()
        val previewTheme = PreviewThemeFragment()
        previewTheme.onClose = {
            requireActivity().supportFragmentManager.popBackStack()
        }
        adapterTheme.onItemClicked = {
            bundle.putSerializable("theme", adapterTheme.dataModelTheme[it])
            bundle.putInt("position", it)
            previewTheme.arguments = bundle
            previewTheme.pushToScreen(activity as MainActivity)

        }
        tvBackTheme.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun initView() {
        rvTheme.layoutManager = LinearLayoutManager(context)
        adapterTheme = RecyclerViewTheme(context)
        adapterTheme.dataModelTheme = Util.getListTheme()
        rvTheme.adapter = adapterTheme
    }
}