package com.example.voicelockscreen.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelFunction
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.fragment_alternative_lock.*


class AlternativeLockFragment : Fragment() {

    private lateinit var adapterFunctionAlternativeLock: RecyclerViewFunctionAlternativeLock

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alternative_lock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()
    }

    private fun initAction() {
        adapterFunctionAlternativeLock.onItemClicked = {
            when (it) {
                0 -> activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                    ?.replace(R.id.content_frame, PinCodeFragment())?.commit()
                1 -> activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                    ?.replace(R.id.content_frame, SetTimerLockFragment())?.commit()
            }
        }
    }

    private fun initView() {
        rvFunctionAlternativeLock.layoutManager = LinearLayoutManager(context)
        adapterFunctionAlternativeLock = RecyclerViewFunctionAlternativeLock(context)
        adapterFunctionAlternativeLock.functionList = Util.getFunctionAlternativeList(resources)
        rvFunctionAlternativeLock.adapter = adapterFunctionAlternativeLock
    }
}