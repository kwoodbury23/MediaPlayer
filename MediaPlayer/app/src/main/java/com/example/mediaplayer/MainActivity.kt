package com.example.mediaplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.media.MediaPlayer
import android.widget.SeekBar
import android.os.Handler
import java.lang.Exception

// This program allows the user to play, pause and stop a song
class MainActivity : AppCompatActivity() {
    // Media player is set to null
    private var mp: MediaPlayer? = null
    // Accesses the song
    private var currentSong: MutableList<Int> = mutableListOf(R.raw.message)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controlSound(currentSong[0])
    }

    // Starts the song and initialized seekbar
    private fun controlSound(id: Int) {
        play.setOnClickListener {
            if (mp == null) {
                mp = MediaPlayer.create(this, id)
                initializeSeekBar()
            }
            mp?.start()
        }
        // Pauses the song
        pause.setOnClickListener {
            if (mp !== null) mp?.pause()
        }
        // Stops the song
        stop.setOnClickListener {
            if (mp !== null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp == null
            }
        }

        // If the song has started the user can see the progress of the song
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                if (fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }

            // There is no seekbar on stop
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }
        })
    }


    // The seekbar is updated with the current position of the song
    private fun initializeSeekBar() {
        seekBar.max = mp!!.duration
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    seekBar.progress = mp!!.currentPosition
                    handler.postDelayed(this, 1000)
                }
                // if the seekbar is null there is no progress on the seekbar
                catch (e: Exception) {
                    seekBar.progress = 0
                }
            }
        }, 0)
    }
}