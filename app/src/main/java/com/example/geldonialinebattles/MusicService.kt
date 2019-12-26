package com.example.geldonialinebattles

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MusicService : Service() {
    lateinit var mPlayer: MediaPlayer
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mPlayer = MediaPlayer.create(this, R.raw.game_music)
        mPlayer.isLooping = true
        mPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        mPlayer.stop()
    }
/*    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mPlayer = MediaPlayer.create(this, R.raw.musket4)
        mPlayer.isLooping = true
        mPlayer.start()
        mPlayer.setOnCompletionListener {
            //mPlayer.stop()
            mPlayer = MediaPlayer.create(this,R.raw.demon_die_2)
            mPlayer.start()
        }
        return START_STICKY
    }*/ //todo working switch for music!!

}