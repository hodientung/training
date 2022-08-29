package com.example.voicelockscreen.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelMediaFile
import kotlinx.android.synthetic.main.fragment_video_files.*
import java.util.ArrayList


class VideoFilesFragment : Fragment() {

    private lateinit var adapterVideoFile: RecyclerViewVideoFile
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        parentFragmentManager.setFragmentResultListener(
            "dataPath",
            this
        ) { _, result ->
            val data = result.getString("folderName")
        }
        return inflater.inflate(R.layout.fragment_video_files, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()

    }

    private fun initAction() {
        TODO("Not yet implemented")
    }

    private fun initView() {
        rvVideoFile.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapterVideoFile = RecyclerViewVideoFile(context)
        //adapterVideoFile.videoList = getVideoList()
        rvVideoFile.adapter = adapterVideoFile
    }

//    private fun getVideoList(): ArrayList<DataModelMediaFile> {
//
//    }

}