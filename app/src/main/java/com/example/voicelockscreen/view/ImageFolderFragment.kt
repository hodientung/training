package com.example.voicelockscreen.view

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelMediaFile
import kotlinx.android.synthetic.main.fragment_image_folder.*
import java.util.ArrayList


class ImageFolderFragment : Fragment() {

    private lateinit var adapterImageFolder: RecyclerViewImageFolder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_folder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()
    }

    private fun initAction() {
        adapterImageFolder.onItemClicked = {
            val bundle = Bundle()
            bundle.putString(
                "folderName",
                adapterImageFolder.folderPath[it].substring(
                    adapterImageFolder.folderPath[it].lastIndexOf("/")
                )
            )
            val imageFilesFragment = ImageFileFragment()
            imageFilesFragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                ?.replace(R.id.content_frame, imageFilesFragment)?.commit()
        }
    }

    private fun initView() {
        rvImageFolder.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapterImageFolder = RecyclerViewImageFolder(context)
        //set data
        adapterImageFolder.dataModelMediaFile = fetchMedia()
        rvImageFolder.adapter = adapterImageFolder
    }

    private fun fetchMedia(): ArrayList<DataModelMediaFile> {
        var cursor: Cursor? = null
        val mediaFiles = arrayListOf<DataModelMediaFile>()
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        try {
            cursor = context?.contentResolver?.query(
                uri, null,
                null, null, null
            )
            if ((cursor != null) && cursor.moveToNext()) {
                do {
                    val id: String =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                    val title: String =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE))
                    val displayName: String =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                    val path: String =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

                    val dataModelMediaFile = DataModelMediaFile(
                        id,
                        title,
                        displayName,
                        size = null,
                        duration = null,
                        path = path,
                        null
                    )
                    val index = path.lastIndexOf("/")
                    val subString = path.substring(0, index)
                    if (!adapterImageFolder.folderPath.contains(subString)) {
                        adapterImageFolder.folderPath.add(subString)
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