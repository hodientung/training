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
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themePinButton
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
            Toast.makeText(context, "Theme $it is updated", Toast.LENGTH_SHORT).show()
            //save setting theme into share preference
            val prefs =
                context?.let { it1 ->
                    PreferenceHelper.customPreference(
                        it1,
                        Util.THEME_SETTING
                    )
                }
            prefs?.themeCode = it
            prefs?.themePinButton = it

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

        item.add(DataModelTheme(R.drawable.background_girl1))
        item.add(DataModelTheme(R.drawable.girl))
        item.add(DataModelTheme(R.drawable.background1))
        item.add(DataModelTheme(R.drawable.background2))
        item.add(DataModelTheme(R.drawable.background3))
        item.add(DataModelTheme(R.drawable.background4))
        item.add(DataModelTheme(R.drawable.background5))
        item.add(DataModelTheme(R.drawable.background16))
        item.add(DataModelTheme(R.drawable.background7))
        item.add(DataModelTheme(R.drawable.background8))

        return item
    }

}