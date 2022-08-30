package com.example.voicelockscreen.view

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelMediaFile
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerControlView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.fragment_video_layer.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class VideoLayerFragment : Fragment() {

    private lateinit var player: ExoPlayer
    private var position = 0
    private var titleVideo = ""
    private var listVideo = arrayListOf<DataModelMediaFile>()

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
        initAction()
    }

    private fun initAction() {
        playVideo()
    }

    private fun playVideo() {
        val pathVideo = listVideo[position].path
        var uri: Uri = Uri.parse(pathVideo)
        context?.let {
            player = ExoPlayer.Builder(it).build()
        }
        exoPlayerView.player = player
        val mediaItem = pathVideo?.let { MediaItem.fromUri(uri) }
        mediaItem?.let { player.setMediaItem(it) }
        player.prepare()
        player.play()


        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                if (isPlaying) {
                    // Active playback.
                } else {
                    // Not playing because playback is paused, ended, suppressed, or the player
                    // is buffering, stopped or failed. Check player.getPlayWhenReady,
                    // player.getPlaybackState, player.getPlaybackSuppressionReason and
                    // player.getPlaybackError for details.
                }
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)

            }

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)

            }
        })
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    private fun releasePlayer() {
        player.release()
    }

    private fun initView() {
        position = arguments?.getInt(position.toString()) ?: 0
        titleVideo = arguments?.getString("title_video").toString()
        listVideo =
            arguments?.getParcelableArrayList<DataModelMediaFile>("video_array_list") as ArrayList<DataModelMediaFile>
    }

}