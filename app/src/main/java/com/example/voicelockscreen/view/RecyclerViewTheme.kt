package com.example.voicelockscreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelTheme
import kotlinx.android.synthetic.main.item_theme.view.*

class RecyclerViewTheme(val context: Context?) :
    RecyclerView.Adapter<RecyclerViewTheme.ThemeViewHolder>() {

    var onItemClicked: ((position: Int) -> Unit)? = null
    var dataModelTheme: ArrayList<DataModelTheme> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewTheme.ThemeViewHolder =
        ThemeViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_theme, parent, false),
            onItemClicked
        )

    override fun onBindViewHolder(holder: RecyclerViewTheme.ThemeViewHolder, position: Int) {
        holder.bind(dataModelTheme[position])
    }

    override fun getItemCount(): Int = dataModelTheme.size

    inner class ThemeViewHolder(itemView: View, onItemClicked: ((Int) -> Unit)?) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.imThemeSet.setOnClickListener {
                onItemClicked?.invoke(adapterPosition)
            }
        }

        fun bind(dataModelTheme: DataModelTheme) {
            itemView.imThemeSet.setBackgroundResource(dataModelTheme.colorTheme)
        }

    }

}