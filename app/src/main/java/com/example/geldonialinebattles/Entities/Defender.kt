package com.example.geldonialinebattles.Entities

import android.widget.ImageView
import com.example.geldonialinebattles.R

//defender test
open class Defender(name: String,health: Int,shootingSkill: Int,
               var expPoints:Int)
    :Entity(name,health,shootingSkill){

    override val EntityImage:Int = R.drawable.bluefis
    override val EntityDeadImage: Int = R.drawable.bluedead
}
//elite
class EliteDefender(name: String,health: Int,shootingSkill: Int,expPoints: Int)
    :Defender(name,health,shootingSkill,expPoints){

    override val EntityImage:Int = R.drawable.bluegren
}