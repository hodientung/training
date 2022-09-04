package com.example.voicelockscreen.view

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.ControlsMode
import com.example.voicelockscreen.model.DataModelMediaFile
import com.example.voicelockscreen.utils.Util.Companion.KEY_PLAYER_PLAY_WHEN_READY
import com.example.voicelockscreen.utils.Util.Companion.KEY_PLAYER_POSITION
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.fragment_video_layer.*
import kotlinx.android.synthetic.main.playback_custom_view.view.*


class VideoLayerFragment : Fragment() {

    private lateinit var player: SimpleExoPlayer
    private var position = 0
    private var titleVideo = ""
    private var listVideo = arrayListOf<DataModelMediaFile>()
    private var controlsMode = ControlsMode.LOCK
    private lateinit var concatenatingMediaSource: ConcatenatingMediaSource
    private var mediaSourceList = arrayListOf<MediaSource>()

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
            player.stop()
            activity?.supportFragmentManager?.popBackStack()

        }
        exoPlayerView.imLock.setOnClickListener {
            controlsMode = ControlsMode.LOCK

            exoPlayerView.rootLayout.visibility = View.INVISIBLE
            exoPlayerView.imUnLock.visibility = View.VISIBLE
            exoPlayerView.imLock.visibility = View.INVISIBLE
            Toast.makeText(context, "Locked", Toast.LENGTH_LONG).show()
        }
        exoPlayerView.imUnLock.setOnClickListener {
            controlsMode = ControlsMode.FULLSCREEN
            exoPlayerView.rootLayout.visibility = View.VISIBLE
            exoPlayerView.imUnLock.visibility = View.INVISIBLE
            exoPlayerView.imLock.visibility = View.VISIBLE
            Toast.makeText(context, "Unlocked", Toast.LENGTH_LONG).show()
        }
        exoPlayerView.imNext.setOnClickListener {
            try {
                player.stop()
                position++
                Log.e("anh", position.toString())
                clearMediaSourceList()
                setupVideo()
            } catch (e: Exception) {
                Toast.makeText(context, "No next video", Toast.LENGTH_LONG).show()
                activity?.supportFragmentManager?.popBackStack()
            }
        }
        exoPlayerView.imPrevious.setOnClickListener {
            try {
                player.stop()
                position--
                clearMediaSourceList()
                setupVideo()
            } catch (e: Exception) {
                Toast.makeText(context, "No previous video", Toast.LENGTH_LONG).show()
                activity?.supportFragmentManager?.popBackStack()
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

        exoPlayerView.imZoom.setOnClickListener {
            exoPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            player.videoScalingMode = C.VIDEO_SCALING_MODE_DEFAULT
            exoPlayerView.imZoom.setImageResource(R.drawable.ic_round_zoom_out_map)
            exoPlayerView.imZoom.setOnClickListener {
                exoPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                player.videoScalingMode = C.VIDEO_SCALING_MODE_DEFAULT
                exoPlayerView.imZoom.setOnClickListener {
                    exoPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                    player.videoScalingMode = C.VIDEO_SCALING_MODE_DEFAULT
                    exoPlayerView.imZoom.setImageResource(R.drawable.ic_round_center_focus_strong)
                    exoPlayerView.imZoom.setOnClickListener {
                        exoPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                        player.videoScalingMode = C.VIDEO_SCALING_MODE_DEFAULT
                        exoPlayerView.imZoom.setImageResource(R.drawable.ic_round_zoom_out_map)
                    }
                }

            }
        }
    }

    private fun setupVideo() {
        player = SimpleExoPlayer.Builder(requireContext()).build()
        val dataSourceFactory = DefaultDataSourceFactory(
            requireContext(),
            Util.getUserAgent(requireContext(), "VoiceLockScreen")
        )
        concatenatingMediaSource = ConcatenatingMediaSource()


        for (i in 0 until listVideo.size) {
            //File(listVideo[i].toString())
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(
                    MediaItem.fromUri(
                        Uri.parse(listVideo[position].path).toString()
                    )
                )
            mediaSourceList.add(mediaSource)
        }
        exoPlayerView.player = player
        exoPlayerView.keepScreenOn = true
        player.addMediaSources(mediaSourceList)
        player.prepare()
        player.play()
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
                    Player.STATE_ENDED -> {
                        //TODO()
                    }
                    Player.STATE_IDLE -> {
                        //TODO()
                    }
                }
            }

            override fun onPlayerError(error: ExoPlaybackException) {
                super.onPlayerError(error)
                Toast.makeText(context, "Video Playing Error", Toast.LENGTH_LONG).show()
            }


        })
        player.playWhenReady = true
    }

    override fun onPause() {
        super.onPause()
        player.playWhenReady = false
        player.playbackState
    }

    override fun onResume() {
        super.onResume()
        player.seekToDefaultPosition()
        player.playWhenReady = true
        player.playbackState
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_PLAYER_POSITION, player.contentPosition)
        outState.putBoolean(KEY_PLAYER_PLAY_WHEN_READY, player.playWhenReady)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            player.seekTo(it.getLong(KEY_PLAYER_POSITION))
            player.playWhenReady = it.getBoolean(KEY_PLAYER_PLAY_WHEN_READY)
        }
    }

    private fun clearMediaSourceList(){
        mediaSourceList.clear()
    }

}