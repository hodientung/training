package com.example.voicelockscreen.view

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelMediaFile
import kotlinx.android.synthetic.main.item_video_folder.view.*
import java.io.File

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
            context?.let {
                Glide.with(it).load(File(getImageOfFolderVideoList(folderPath)))
                    .into(itemView.imVideoFolder)
            }
        }

        private fun getImageOfFolderVideoList(folderPath: String): String {
            var path = ""
            var cursor: Cursor? = null
            val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            val selection = MediaStore.Video.Media.DATA + " like?"
            val selectionArg: Array<String> = arrayOf(
                "%${
                    folderPath.substring(
                        folderPath.lastIndexOf("/")
                    )
                }%"
            )
            try {
                cursor =
                    context?.contentResolver?.query(uri, null, selection, selectionArg, null)
                if ((cursor != null) && cursor.moveToNext()) {
                    path =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                            ?: "abc"
                }
            } finally {
                cursor?.close()
            }
            return path
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