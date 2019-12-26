package com.example.geldonialinebattles

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool

class SoundPlayer(context:Context) {
    var soundPool:SoundPool = SoundPool(2,AudioManager.STREAM_MUSIC,0)
    var musketSound:Int = 0
    var bowSound:Int = 0
    var demonSound:Int = 0

    init {
        musketSound = soundPool.load(context,R.raw.musket4,1)
        bowSound = soundPool.load(context,R.raw.bow_shoot_08,1)
        demonSound = soundPool.load(context,R.raw.demon_die_2,1)
    }

    fun playMusketSound(){
        soundPool.play(musketSound,1f,1f,1,0,1f)
    }

    fun playBowSound(){
        soundPool.play(bowSound,1f,1f,1,0,1f)
    }

    fun playDemonSound(){
        soundPool.play(demonSound,1f,1f,1,0,1f)
    }
}
