package com.example.appplantas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class SoyUnFragmentoFragment : Fragment() {

    private val TAG = "SoyUnFragmentoFragment"
    private val viewModel: VideosViewModel by activityViewModels()

    private var player1: YouTubePlayer? = null
    private var player2: YouTubePlayer? = null
    private var player3: YouTubePlayer? = null
    private var player4: YouTubePlayer? = null

    private var playersInitialized = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_soy_un_fragmento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ytView1 = view.findViewById<YouTubePlayerView>(R.id.youtube_player_1)
        val ytView2 = view.findViewById<YouTubePlayerView>(R.id.youtube_player_2)
        val ytView3 = view.findViewById<YouTubePlayerView>(R.id.youtube_player_3)
        val ytView4 = view.findViewById<YouTubePlayerView>(R.id.youtube_player_4)

        viewLifecycleOwner.lifecycle.addObserver(ytView1)
        viewLifecycleOwner.lifecycle.addObserver(ytView2)
        viewLifecycleOwner.lifecycle.addObserver(ytView3)
        viewLifecycleOwner.lifecycle.addObserver(ytView4)

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            if (playersInitialized) return@observe
            playersInitialized = true

            initPlayer(ytView1, uiState.videoId1) { player1 = it }
            initPlayer(ytView2, uiState.videoId2) { player2 = it }
            initPlayer(ytView3, uiState.videoId3) { player3 = it }
            initPlayer(ytView4, uiState.videoId4) { player4 = it }
        }
    }

    private fun initPlayer(
        playerView: YouTubePlayerView,
        videoId: String,
        onReady: (YouTubePlayer) -> Unit
    ) {
        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                onReady(youTubePlayer)
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        player1?.pause()
        player2?.pause()
        player3?.pause()
        player4?.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player1 = null
        player2 = null
        player3 = null
        player4 = null
        playersInitialized = false
    }
}
