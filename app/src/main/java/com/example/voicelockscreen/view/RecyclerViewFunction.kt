package com.example.voicelockscreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelFunction
import kotlinx.android.synthetic.main.item_function.view.*

class RecyclerViewFunction(val context: Context?) :
    RecyclerView.Adapter<RecyclerViewFunction.FunctionViewHolder>() {


    var onItemClicked: ((position: Int) -> Unit)? = null
    var functionList: ArrayList<DataModelFunction> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewFunction.FunctionViewHolder = FunctionViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_function, parent, false
        ), onItemClicked
    )

    override fun onBindViewHolder(
        holder: RecyclerViewFunction.FunctionViewHolder,
        position: Int
    ) {
        holder.bind(functionList[position])
    }

    override fun getItemCount(): Int = functionList.size

    inner class FunctionViewHolder(itemView: View, onItemClicked: ((Int) -> Unit)?) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.ctContentFunction.setOnClickListener {
                onItemClicked?.invoke(adapterPosition)
            }
        }

        fun bind(dataModelFunction: DataModelFunction) {
            itemView.tvName.text = dataModelFunction.nameFunction
            itemView.imFunction.setImageResource(dataModelFunction.iconFunction)
        }
    }
}