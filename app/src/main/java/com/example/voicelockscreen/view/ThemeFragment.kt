package com.example.voicelockscreen.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelTheme
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themeCode
import com.example.voicelockscreen.utils.Util
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
        adapterTheme.onItemClicked = {
            Toast.makeText(context, "Vi tri $it", Toast.LENGTH_SHORT).show()
            //send broad cast den cac man chua layout set theme
            val prefs =
                context?.let { it1 ->
                    PreferenceHelper.customPreference(
                        it1,
                        Util.THEME_SETTING
                    )
                }
            prefs?.themeCode = it

        }
    }

    private fun initView() {
        rvTheme.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapterTheme = RecyclerViewTheme(context)
        adapterTheme.dataModelTheme = getListTheme()
        rvTheme.adapter = adapterTheme
    }

    private fun getListTheme(): ArrayList<DataModelTheme> {
        val item = arrayListOf<DataModelTheme>()

        item.add(DataModelTheme(R.color.purple_200))
        item.add(DataModelTheme(R.color.purple_500))
        item.add(DataModelTheme(R.color.purple_700))
        item.add(DataModelTheme(R.color.teal_200))
        item.add(DataModelTheme(R.color.teal_700))
        item.add(DataModelTheme(R.color.black))
        item.add(DataModelTheme(R.color.white))
        item.add(DataModelTheme(R.color.color_502A5E))

        return item
    }

}