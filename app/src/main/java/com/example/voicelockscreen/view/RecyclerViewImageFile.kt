package com.example.voicelockscreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelMediaFile
import kotlinx.android.synthetic.main.item_image_file.view.*
import java.io.File

class RecyclerViewImageFile(val context: Context?) :
    RecyclerView.Adapter<RecyclerViewImageFile.ImageFileViewHolder>() {


    var onItemClicked: ((position: Int) -> Unit)? = null
    var imageList: ArrayList<DataModelMediaFile> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewImageFile.ImageFileViewHolder = ImageFileViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_image_file, parent, false
        ), onItemClicked
    )

    override fun onBindViewHolder(
        holder: RecyclerViewImageFile.ImageFileViewHolder,
        position: Int
    ) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size

    inner class ImageFileViewHolder(itemView: View, onItemClicked: ((Int) -> Unit)?) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.ctItemImageFile.setOnClickListener {
                onItemClicked?.invoke(adapterPosition)
            }
        }

        fun bind(dataModelMediaFile: DataModelMediaFile) {
            itemView.tvFileImageName.text = dataModelMediaFile.name
            itemView.tvImagePath.text = dataModelMediaFile.path
            context?.let {
                if (!dataModelMediaFile.path.isNullOrEmpty())
                    Glide.with(it).load(File(dataModelMediaFile.path)).into(itemView.imImageFile)
            }

        }

    }
}