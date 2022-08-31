package com.example.voicelockscreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelMediaFile
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.item_video_file.view.*
import kotlinx.android.synthetic.main.item_video_folder.view.*
import java.io.File

class RecyclerViewVideoFile(val context: Context?) :
    RecyclerView.Adapter<RecyclerViewVideoFile.VideoFileViewHolder>() {


    var onItemClicked: ((position: Int) -> Unit)? = null
    var videoList: ArrayList<DataModelMediaFile> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewVideoFile.VideoFileViewHolder = VideoFileViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_video_file, parent, false
        ), onItemClicked
    )

    override fun onBindViewHolder(
        holder: RecyclerViewVideoFile.VideoFileViewHolder,
        position: Int
    ) {
        holder.bind(videoList[position])
    }

    override fun getItemCount(): Int = videoList.size

    inner class VideoFileViewHolder(itemView: View, onItemClicked: ((Int) -> Unit)?) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.rvContent.setOnClickListener {
                onItemClicked?.invoke(adapterPosition)
            }
        }

        fun bind(dataModelMediaFile: DataModelMediaFile) {
            itemView.tvVideoName.text = dataModelMediaFile.name
            val size = dataModelMediaFile.size
            itemView.tvVideoSize.text =
                android.text.format.Formatter.formatFileSize(context, size?.toLong() ?: 0L)
            val milliSeconds = dataModelMediaFile.duration?.toDouble()
            itemView.videoDuration.text = Util.timeConversion(milliSeconds?.toLong() ?:0L )
            context?.let { Glide.with(it).load(File(dataModelMediaFile.path)).into(itemView.imThumbnail) }

        }

    }
}