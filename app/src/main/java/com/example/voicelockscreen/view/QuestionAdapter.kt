package com.example.voicelockscreen.view


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.voicelockscreen.R
import kotlinx.android.synthetic.main.item_spinner.view.*


class QuestionAdapter(val context: Context?) : BaseAdapter() {
    var listQuestion: ArrayList<String> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int = listQuestion.size

    override fun getItem(p0: Int): Any = p0

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_spinner, p2, false)
        view.tvQuestion.text = listQuestion[p0]

        return view
    }

}