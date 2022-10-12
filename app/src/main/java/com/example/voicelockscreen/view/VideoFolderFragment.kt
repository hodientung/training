package com.example.voicelockscreen.view

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelMediaFile
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import kotlinx.android.synthetic.main.fragment_video_folder.*
import java.util.ArrayList

class VideoFolderFragment : Fragment() {

    private lateinit var adapterVideoFolder: RecyclerViewVideoFolder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_folder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()

    }

    private fun initAction() {
        adapterVideoFolder.onItemClicked = {
            val bundle = Bundle()
            bundle.putString(
                "folderName",
                adapterVideoFolder.folderPath[it].substring(
                    adapterVideoFolder.folderPath[it].lastIndexOf("/") + 1
                )
            )
            val videoFilesFragment = VideoFilesFragment()
            videoFilesFragment.arguments = bundle
            videoFilesFragment.pushToScreen(activity as MainActivity)
        }
        tvBackImage1.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val mediaFilesFirst = arrayListOf<DataModelMediaFile>()
                val folderPath = arrayListOf<String>()
                val inputs = newText?.lowercase().toString()
                val mediaFiles = fetchMedia()
                for (i in 0 until mediaFiles.size) {
                    val index: Int = mediaFiles[i].path?.lastIndexOf("/") ?: 0
                    val subString = mediaFiles[i].path?.substring(0, index) ?: ""
                    if (subString.lowercase().contains(inputs)) {
                        mediaFilesFirst.add(mediaFiles[i])
                        if (!folderPath.contains(subString)) {
                            folderPath.add(subString)
                        }
                    }
                }
                adapterVideoFolder.dataModelMediaFile = mediaFilesFirst
                adapterVideoFolder.folderPath = folderPath

                return true
            }

        })
    }

    private fun initView() {
        rvVideoFolder.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapterVideoFolder = RecyclerViewVideoFolder(context)
        //set data
        adapterVideoFolder.dataModelMediaFile = fetchMedia()
        rvVideoFolder.adapter = adapterVideoFolder
    }

    private fun fetchMedia(): ArrayList<DataModelMediaFile> {
        var cursor: Cursor? = null
        val mediaFiles = arrayListOf<DataModelMediaFile>()
        val uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        try {
            cursor = context?.contentResolver?.query(
                uri, null,
                null, null, null
            )
            if (cursor != null && cursor.moveToNext()) {
                do {
                    val id: String =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
                    val title: String =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
                    val displayName: String =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
                    val size: String =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE))
                    val duration: String =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))
                    val path: String =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                    val dateAdded: String =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED))
                    val dataModelMediaFile = DataModelMediaFile(
                        id,
                        title,
                        displayName,
                        size,
                        duration,
                        path,
                        dateAdded
                    )
                    val index = path.lastIndexOf("/")
                    val subString = path.substring(0, index)
                    if (!adapterVideoFolder.folderPath.contains(subString)) {
                        adapterVideoFolder.folderPath.add(subString)
                    }
                    mediaFiles.add(dataModelMediaFile)
                } while (cursor.moveToNext())
            }
        } finally {
            cursor?.close()
        }
        return mediaFiles
    }

}
