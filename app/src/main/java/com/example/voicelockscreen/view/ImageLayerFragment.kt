package com.example.voicelockscreen.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.voicelockscreen.R
import kotlinx.android.synthetic.main.fragment_image_layer.*

class ImageLayerFragment : Fragment() {
    private var path = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_layer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupImage()
    }

    private fun setupImage() {
        path = arguments?.getString("path").toString()
        Glide.with(requireContext()).load(path).into(imImageLayer)
    }
}