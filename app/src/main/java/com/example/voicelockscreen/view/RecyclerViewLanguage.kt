package com.example.voicelockscreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelSetting
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.positionSelect
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.item_languaga.view.*

class RecyclerViewLanguage(val context: Context?) :
    RecyclerView.Adapter<RecyclerViewLanguage.LanguageViewHolder>() {


    var onItemClicked: ((position: Int) -> Unit)? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var dataModelSetting: ArrayList<DataModelSetting> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var selectedPosition = 0


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewLanguage.LanguageViewHolder = LanguageViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_languaga, parent, false
        ), onItemClicked
    )

    override fun onBindViewHolder(
        holder: RecyclerViewLanguage.LanguageViewHolder,
        position: Int
    ) {
        holder.bind(dataModelSetting[position])
    }

    override fun getItemCount(): Int = dataModelSetting.size

    inner class LanguageViewHolder(
        itemView: View,
        onItemClicked: ((Int) -> Unit)?
    ) :
        RecyclerView.ViewHolder(itemView) {
        init {
        }

        fun bind(dataModelSetting: DataModelSetting) {
            val prefs =
                context?.let { PreferenceHelper.customPreference(it, Util.DATA_LANGUAGE_APP) }
            dataModelSetting.imageSetting?.let { itemView.imLanguage.setImageResource(it) }
            itemView.tvLanguage.text = dataModelSetting.text

            itemView.rbLanguage.isChecked = adapterPosition == prefs?.positionSelect
            itemView.rbLanguage.setOnCheckedChangeListener { _, b ->
                if (b) {
                    selectedPosition = adapterPosition
                    prefs?.positionSelect = selectedPosition
                    itemView.rbLanguage.setOnClickListener {
                        onItemClicked?.invoke(selectedPosition)
                    }

                }
            }
        }
    }
}