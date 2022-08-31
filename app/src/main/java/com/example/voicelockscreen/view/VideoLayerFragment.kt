package com.example.voicelockscreen.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.ControlsMode
import com.example.voicelockscreen.model.DataModelMediaFile
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.fragment_video_layer.*
import kotlinx.android.synthetic.main.playback_custom_view.view.*
import java.io.File


class VideoLayerFragment : Fragment() {

    private lateinit var player: ExoPlayer
    private var position = 0
    private var titleVideo = ""
    private var listVideo = arrayListOf<DataModelMediaFile>()
    private var controlsMode = ControlsMode.LOCK
    private var restoredMediaItem = 0
    private var seekTime = 0L
    private lateinit var concatenatingMediaSource: ConcatenatingMediaSource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_layer, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setupVideo()
        initAction()
    }

    private fun initAction() {
        exoPlayerView.tvBack.setOnClickListener {
            player.release()
            activity?.supportFragmentManager?.popBackStack()

        }
        exoPlayerView.imLock.setOnClickListener {
            controlsMode = ControlsMode.FULLSCREEN
            exoPlayerView.rootLayout.visibility = View.VISIBLE
            exoPlayerView.imUnLock.visibility = View.INVISIBLE
            exoPlayerView.imLock.visibility = View.VISIBLE
            Toast.makeText(context, "Locked", Toast.LENGTH_LONG).show()
        }
        exoPlayerView.imUnLock.setOnClickListener {
            controlsMode = ControlsMode.LOCK
            exoPlayerView.rootLayout.visibility = View.INVISIBLE
            exoPlayerView.imUnLock.visibility = View.VISIBLE
            exoPlayerView.imLock.visibility = View.INVISIBLE
            Toast.makeText(context, "Unlocked", Toast.LENGTH_LONG).show()
        }
        exoPlayerView.imNext.setOnClickListener {
            try {
                player.stop()
                position++
                playVideo()
            } catch (e: Exception) {
                Toast.makeText(context, "No next video", Toast.LENGTH_LONG).show()
            }
        }
        exoPlayerView.imPrevious.setOnClickListener {
            try {
                player.stop()
                position--
                playVideo()
            } catch (e: Exception) {
                Toast.makeText(context, "No previous video", Toast.LENGTH_LONG).show()
            }
        }
        exoPlayerView.imPlay.setOnClickListener {
            playVideo()
            exoPlayerView.imPlay.visibility = View.INVISIBLE
            exoPlayerView.imPause.visibility = View.VISIBLE

        }
        exoPlayerView.imPause.setOnClickListener {
            pausePlayer()
            exoPlayerView.imPlay.visibility = View.VISIBLE
            exoPlayerView.imPause.visibility = View.INVISIBLE
        }
    }

    private fun setupVideo() {
        player = ExoPlayer.Builder(requireContext()).build()
        val dataSourceFactory = DefaultDataSourceFactory(
            requireContext(),
            Util.getUserAgent(requireContext(), "VoiceLockScreen")
        )
        concatenatingMediaSource = ConcatenatingMediaSource()


        for (i in 0 until listVideo.size - 1) {
            File(listVideo[i].toString())
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(
                    MediaItem.fromUri(
                        Uri.parse(listVideo[position].path).toString()
                    )
                )
            concatenatingMediaSource.addMediaSource(mediaSource)
        }
        exoPlayerView.player = player
        exoPlayerView.keepScreenOn = true
        player.prepare(concatenatingMediaSource)
        player.seekTo(position, C.TIME_UNSET)
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                super.onPlaybackStateChanged(state)
                when (state) {
                    Player.STATE_BUFFERING -> {
                        progressBar.visibility = View.VISIBLE

                    }
                    Player.STATE_READY -> {
                        progressBar.visibility = View.INVISIBLE
                    }
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                Toast.makeText(context, "Video Playing Error", Toast.LENGTH_LONG).show()
            }

        })
        player.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        player.playWhenReady = false
        player.playbackState
        player.release()
    }

    override fun onResume() {
        super.onResume()
        player.seekToDefaultPosition()
        player.playWhenReady = true
        player.playbackState
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("SeekTime", player.currentPosition)
        // current mediaItem
        outState.putInt("mediaItem", player.currentWindowIndex)
    }

    private fun playVideo() {
        player.playWhenReady = true
        player.playbackState
    }

    private fun pausePlayer() {
        player.playWhenReady = false
        player.playbackState
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("Current position", player.currentPosition.toString())
    }

    private fun initView() {
        position = arguments?.getInt("position") ?: 0
        titleVideo = arguments?.getString("title_video").toString()
        listVideo =
            arguments?.getParcelableArrayList<DataModelMediaFile>("video_array_list") as ArrayList<DataModelMediaFile>
    }
}