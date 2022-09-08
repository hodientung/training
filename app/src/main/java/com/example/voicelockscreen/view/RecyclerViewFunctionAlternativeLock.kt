package com.example.voicelockscreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelFunction
import kotlinx.android.synthetic.main.item_alternative_lock.view.*


class RecyclerViewFunctionAlternativeLock(val context: Context?) :
    RecyclerView.Adapter<RecyclerViewFunctionAlternativeLock.FunctionAlternativeLockViewHolder>() {


    var onItemClicked: ((position: Int) -> Unit)? = null
    var functionList: ArrayList<DataModelFunction> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewFunctionAlternativeLock.FunctionAlternativeLockViewHolder =
        FunctionAlternativeLockViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_alternative_lock, parent, false
            ), onItemClicked
        )

    override fun onBindViewHolder(
        holder: RecyclerViewFunctionAlternativeLock.FunctionAlternativeLockViewHolder,
        position: Int
    ) {
        holder.bind(functionList[position])
    }

    override fun getItemCount(): Int = functionList.size

    inner class FunctionAlternativeLockViewHolder(itemView: View, onItemClicked: ((Int) -> Unit)?) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.ctAlternativeLock.setOnClickListener {
                onItemClicked?.invoke(adapterPosition)
            }
        }

        fun bind(dataModelFunction: DataModelFunction) {
            itemView.tvNameFunctionAlternativeLock.text = dataModelFunction.nameFunction
            itemView.imageView1AlternativeLock.setBackgroundResource(dataModelFunction.iconFunction)
        }
    }
}