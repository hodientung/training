package com.example.voicelockscreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelMediaFile
import kotlinx.android.synthetic.main.item_video_folder.view.*

class RecyclerViewVideoFolder(val context: Context?) :
    RecyclerView.Adapter<RecyclerViewVideoFolder.VideoFolderViewHolder>() {

    var onItemClicked: ((position: Int) -> Unit)? = null
    var dataModelMediaFile: ArrayList<DataModelMediaFile> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var folderPath: ArrayList<String> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoFolderViewHolder =
        VideoFolderViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_video_folder, parent, false),
            onItemClicked
        )

    override fun onBindViewHolder(holder: VideoFolderViewHolder, position: Int) {
        holder.bind(folderPath[position])
    }

    override fun getItemCount(): Int = folderPath.size

    inner class VideoFolderViewHolder(itemView: View, onItemClicked: ((Int) -> Unit)?) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.ctItemVideoFolder.setOnClickListener {
                onItemClicked?.invoke(adapterPosition)
            }
        }

        fun bind(folderPath: String) {
            val indexPath: Int = folderPath.lastIndexOf("/")
            val nameOfFolder = folderPath.substring(indexPath + 1)
            itemView.tvFolderName.text = nameOfFolder
            itemView.tvFolderPath.text = folderPath
            itemView.tvVideoNumber.text =
                getNumberOfVideos(folderPath).toString() + " Videos"
        }

        private fun getNumberOfVideos(folderName: String): Int {
            var numberOfVideo = 0
            dataModelMediaFile.forEach {
                if (it.path?.substring(0, it.path.lastIndexOf("/"))?.endsWith(folderName) == true)
                    numberOfVideo++
            }
            return numberOfVideo
        }

    }
}