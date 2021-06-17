package com.example.myrtmpplayer

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.video.MediaCodecVideoRenderer
import com.google.android.exoplayer2.video.spherical.CameraMotionRenderer


class MainActivity : AppCompatActivity() {

    private var player: SimpleExoPlayer? = null
    private var mediaSource: MediaSource? = null
    private var newPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        play()

        findViewById<Button>(R.id.retry).setOnClickListener {
            retry()
        }
    }

    private fun play() {
        //player = SimpleExoPlayer.Builder(this).build()
        newPlayer = ExoPlayer.Builder(this, CameraMotionRenderer()).build()
        val playerView = findViewById<PlayerView>(R.id.player_view)
        val uri = Uri.parse(BuildConfig.STREAMING_URL)

        //playerView.player = player
        playerView.player = newPlayer

        /*val mediaSource = ExtractorMediaSource(
            uri,
            RtmpDataSourceFactory(),
            DefaultExtractorsFactory(),
            null,
            null
        )
        player?.prepare(mediaSource)
        player?.playWhenReady = true*/

        val mediaItem = MediaItem.fromUri(uri)
        val newMediaSource : MediaSource = ProgressiveMediaSource.Factory(RtmpDataSourceFactory()).createMediaSource(mediaItem)
        newPlayer?.setMediaSource(newMediaSource)
        newPlayer?.playWhenReady = true
    }

    private fun stop() {
        player?.playWhenReady = false
        player?.release()
    }

    private fun retry() {
        stop()
        play()
    }
}