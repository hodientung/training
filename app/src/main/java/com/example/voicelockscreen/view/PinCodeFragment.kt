package com.example.voicelockscreen.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModel
import kotlinx.android.synthetic.main.fragment_pin_code.*


class PinCodeFragment : Fragment() {

    private lateinit var mAdapter: RecyclerViewPinLock

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pin_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {
        rvPinCode.layoutManager = GridLayoutManager(context, 3)
        mAdapter = RecyclerViewPinLock {
            Toast.makeText(context, it.number, Toast.LENGTH_SHORT).show()

        }
        mAdapter.dataModel = getListNumber()
        rvPinCode.adapter = mAdapter
    }

    private fun getListNumber(): ArrayList<DataModel> {
        val item = arrayListOf<DataModel>()
        item.add(DataModel("1"))
        item.add(DataModel("2"))
        item.add(DataModel("3"))
        item.add(DataModel("4"))
        item.add(DataModel("5"))
        item.add(DataModel("6"))
        item.add(DataModel("7"))
        item.add(DataModel("8"))
        item.add(DataModel("9"))
        item.add(DataModel("0"))
        return item
    }

    companion object {
        const val TAG = "PinCodeFragment"
    }
}