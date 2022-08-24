package com.example.voicelockscreen.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModel
import kotlinx.android.synthetic.main.item_number.view.*

class RecyclerViewPinLock(
    private val onItemClicked: (DataModel) -> Unit
) :
    RecyclerView.Adapter<RecyclerViewPinLock.ViewHolderPinLock>() {
    var dataModel: ArrayList<DataModel> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderPinLock {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_number, parent, false)
        return ViewHolderPinLock(view) {
            onItemClicked(dataModel[it])
        }
    }

    override fun onBindViewHolder(holder: ViewHolderPinLock, position: Int) {
        holder.bind(dataModel[position])
    }

    override fun getItemCount(): Int = dataModel.size

    class ViewHolderPinLock(itemView: View, onItemClicked: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.btnNumber.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        fun bind(dataModel: DataModel) {
            itemView.btnNumber.text = dataModel.number
        }
    }

}