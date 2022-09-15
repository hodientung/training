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
        adapterTheme.onItemClicked = {
            bundle.putSerializable("theme", adapterTheme.dataModelTheme[it])
            bundle.putInt("position", it)
            previewTheme.arguments = bundle
            previewTheme.pushToScreen(activity as MainActivity)

        }
    }

    private fun initView() {
        rvTheme.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        adapterTheme = RecyclerViewTheme(context)
        adapterTheme.dataModelTheme = getListTheme()
        rvTheme.adapter = adapterTheme
    }

    private fun getListTheme(): ArrayList<DataModelTheme> {
        val item = arrayListOf<DataModelTheme>()

        item.add(DataModelTheme(colorTheme = R.drawable.t6, name = " Theme One"))
        item.add(DataModelTheme(colorTheme = R.drawable.t4, name = "Theme Two"))
        item.add(DataModelTheme(colorTheme = R.drawable.themec, name = "Theme Three"))
        item.add(DataModelTheme(colorTheme = R.drawable.themee, name = "Theme Four"))
        item.add(DataModelTheme(colorTheme = R.drawable.themeb, name = "Theme Five"))
        item.add(DataModelTheme(colorTheme = R.drawable.thema, name = "Theme Six"))
        item.add(DataModelTheme(colorTheme = R.drawable.t1, name = "Theme Seven"))
        item.add(DataModelTheme(colorTheme = R.drawable.t2, name = "Theme Eight"))
        item.add(DataModelTheme(colorTheme = R.drawable.t3, name = "Theme Nine"))
        item.add(DataModelTheme(colorTheme = R.drawable.t7, name = "Theme Ten"))

        return item
    }

}