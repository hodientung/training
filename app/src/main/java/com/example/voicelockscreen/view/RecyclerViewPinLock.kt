package com.example.voicelockscreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModel
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.fragment_preview_theme.*
import kotlinx.android.synthetic.main.item_delete.view.*
import kotlinx.android.synthetic.main.item_empty.view.*
import kotlinx.android.synthetic.main.item_number.view.*

class RecyclerViewPinLock(
    val context: Context?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClicked: ((position: Int) -> Unit)? = null
    var dataModel: ArrayList<DataModel> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class CircleViewViewHolder(itemView: View, onItemClicked: ((Int) -> Unit)?) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.btnNumber.setOnClickListener {
                onItemClicked?.invoke(adapterPosition)
            }
        }

        fun bind(dataModel: DataModel) {
            // show data
            dataModel.backgroundPinButton?.let { itemView.btnNumber.setImageResource(it) }
            itemView.tvNumber.text = dataModel.number
            itemView.tvNumber.typeface =
                dataModel.typeFace?.let { context?.let { it1 -> ResourcesCompat.getFont(it1, it) } }
            //dataModel.backgroundPinButton?.let { itemView.btnNumber.setBackgroundResource(it) }
        }

    }

    private inner class CircleEmptyViewViewHolder(itemView: View, onItemClicked: ((Int) -> Unit)?) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.btnNumberEmpty.setOnClickListener {
                onItemClicked?.invoke(adapterPosition)
            }
        }

        fun bind(dataModel: DataModel) {
            // show data
        }

    }

    private inner class CircleDeleteViewViewHolder(
        itemView: View,
        onItemClicked: ((Int) -> Unit)?
    ) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.btnNumberDelete.setOnClickListener {
                onItemClicked?.invoke(adapterPosition)
            }
        }

        fun bind(dataModel: DataModel?) {

            dataModel?.colorDelete?.let {
                context?.let { context ->
                    itemView.icon.setColorFilter(ContextCompat.getColor(context, it))
                }
            }
            // show data
            //dataModel.backgroundPinButton?.let { itemView.btnNumberDelete.setBackgroundResource(it) }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = when (viewType) {
        Util.THE_FIRST_VIEW ->
            CircleViewViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_number, parent, false),
                onItemClicked
            )

        Util.THE_SECOND_VIEW ->
            CircleEmptyViewViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_empty, parent, false),
                onItemClicked
            )

        else ->
            CircleDeleteViewViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_delete, parent, false),
                onItemClicked
            )
//        val view =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_number, parent, false)
//        return ViewHolderPinLock(view) {
//            onItemClicked(dataModel[it])
//        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            position in 0..8 || position == 10 && dataModel[position].viewType == 1
            ->
                (holder as CircleViewViewHolder).bind(dataModel[position])

            position == 9 && dataModel[position].viewType == 2 ->
                (holder as CircleEmptyViewViewHolder).bind(dataModel[position])
            else ->
                (holder as CircleDeleteViewViewHolder).bind(dataModel[position])
        }
    }

    override fun getItemViewType(position: Int): Int = dataModel[position].viewType
    override fun getItemCount(): Int = dataModel.size

}