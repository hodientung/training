package com.example.voicelockscreen.view

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelMediaFile
import kotlinx.android.synthetic.main.fragment_video_files.*
import java.util.ArrayList


class VideoFilesFragment : Fragment() {

    private lateinit var adapterVideoFile: RecyclerViewVideoFile
    private var folderName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video_files, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFolderName()
        initView()
        initAction()

    }

    private fun getFolderName() {
        folderName = arguments?.getString("folderName").toString()
    }

    private fun initAction() {
        adapterVideoFile.onItemClicked = {
            //Open fragment to see video
            val bundle = Bundle()
            bundle.putInt("position", it)
            bundle.putString("title_video", adapterVideoFile.videoList[it].name)
            bundle.putParcelableArrayList("video_array_list", adapterVideoFile.videoList)
            val videoLayerFragment = VideoLayerFragment()
            videoLayerFragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                ?.replace(R.id.content_frame, videoLayerFragment)?.commit()
        }
    }

    private fun initView() {
        rvVideoFile.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapterVideoFile = RecyclerViewVideoFile(context)
        adapterVideoFile.videoList = getVideoList(folderName)
        rvVideoFile.adapter = adapterVideoFile
    }

    private fun getVideoList(folderName: String): ArrayList<DataModelMediaFile> {
        val videoFiles = arrayListOf<DataModelMediaFile>()
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Video.Media.DATA + " like?"
        val selectionArg: Array<String> = arrayOf("%$folderName%")
        val cursor = context?.contentResolver?.query(uri, null, selection, selectionArg, null)
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
                videoFiles.add(dataModelMediaFile)
            } while (cursor.moveToNext())
        }
        return videoFiles
    }

}