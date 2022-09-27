package com.example.voicelockscreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelSetting
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.clearValues
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isShowTime
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.item_setting.view.*

class RecyclerViewSetting(val context: Context?) :
    RecyclerView.Adapter<RecyclerViewSetting.SettingViewHolder>() {


    var onItemClicked: ((position: Int) -> Unit)? = null
    var dataModelSetting: ArrayList<DataModelSetting> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewSetting.SettingViewHolder = SettingViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_setting, parent, false
        ), onItemClicked
    )

    override fun onBindViewHolder(
        holder: RecyclerViewSetting.SettingViewHolder,
        position: Int
    ) {
        holder.bind(dataModelSetting[position])
    }

    override fun getItemCount(): Int = dataModelSetting.size

    inner class SettingViewHolder(
        itemView: View,
        onItemClicked: ((Int) -> Unit)?
    ) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.ctSetting.setOnClickListener {
                onItemClicked?.invoke(adapterPosition)
            }
        }

        private fun handleSwitchStateDateAndTime() {
            val prefs =
                context?.let { it1 ->
                    PreferenceHelper.customPreference(
                        it1,
                        Util.TIME_DEVICE
                    )
                }
            itemView.imRound.isChecked = prefs?.isShowTime == true
            itemView.imRound.setOnClickListener {
                if (itemView.imRound.isChecked) {
                    Toast.makeText(
                        context,
                        context?.getString(R.string.show_time),
                        Toast.LENGTH_LONG
                    ).show()
                    prefs?.isShowTime = true
                } else {
                    prefs?.isShowTime = false
                    Toast.makeText(
                        context,
                        context?.getString(R.string.hidden_time),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        fun bind(dataModelSetting: DataModelSetting) {
            dataModelSetting.imageSetting?.let { itemView.imSetting.setImageResource(it) }
            if (adapterPosition == 1)
                itemView.imRound.visibility = View.VISIBLE
            else
                itemView.imRound.visibility = View.GONE
            itemView.tvName.text = dataModelSetting.text
            handleSwitchStateDateAndTime()
        }

    }
}