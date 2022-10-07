package com.example.voicelockscreen.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.codeLanguage
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.fragment_language.*


class LanguageFragment : Fragment() {
    private lateinit var mAdapter: RecyclerViewLanguage
     var onClose: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_language, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()
    }

    private fun initAction() {
        val prefs = PreferenceHelper.customPreference(requireContext(), Util.DATA_LANGUAGE_APP)
        mAdapter.onItemClicked = {
            prefs.codeLanguage = mAdapter.dataModelSetting[it].code
        }
        check.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            onClose?.invoke()
        }
    }

    private fun initView() {
        rvLanguage.layoutManager = LinearLayoutManager(context)
        mAdapter = RecyclerViewLanguage(context)
        mAdapter.dataModelSetting = Util.getListItemLanguage(requireContext())
        rvLanguage.adapter = mAdapter
    }

}