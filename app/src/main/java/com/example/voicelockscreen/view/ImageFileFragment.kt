package com.example.voicelockscreen.view

import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelMediaFile
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import kotlinx.android.synthetic.main.fragment_image_file.*
import java.util.ArrayList

class ImageFileFragment : Fragment() {

    private lateinit var adapterImageFile: RecyclerViewImageFile
    private var folderName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_file, container, false)
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
        adapterImageFile.onItemClicked = {
            //Open fragment to see video
            val bundle = Bundle()
            bundle.putString("path", adapterImageFile.imageList[it].path)
            val imageLayerFragment = ImageLayerFragment()
            imageLayerFragment.arguments = bundle
            imageLayerFragment.pushToScreen(activity as MainActivity)
        }
    }

    private fun initView() {
        rvImageFile.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapterImageFile = RecyclerViewImageFile(context)
        adapterImageFile.imageList = getImageList(folderName)
        rvImageFile.adapter = adapterImageFile
    }

    private fun getImageList(folderName: String): ArrayList<DataModelMediaFile> {
        var cursor: Cursor? = null
        val imageFiles = arrayListOf<DataModelMediaFile>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Images.Media.DATA + " like?"
        val selectionArg: Array<String> = arrayOf("%$folderName%")
        try {
            cursor = context?.contentResolver?.query(uri, null, selection, selectionArg, null)
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
                        null,
                        null,
                        path = path, null
                    )
                    imageFiles.add(dataModelMediaFile)
                } while (cursor.moveToNext())
            }
        } finally {
            cursor?.close()
        }

        return imageFiles
    }
}