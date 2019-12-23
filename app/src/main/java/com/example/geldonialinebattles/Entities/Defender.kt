package com.example.geldonialinebattles.Entities

import android.widget.ImageView
import com.example.geldonialinebattles.R

//defenders
open class Defender(name: String,health: Int,shootingSkill: Int)
    :Entity(name,health,shootingSkill){

    override val EntityImage:Int = R.drawable.bluefis
    //override val EntityDeadImage: Int = R.drawable.bluedead
}
//elite
class EliteDefender(name: String,health: Int,shootingSkill: Int)
    :Defender(name,health,shootingSkill){

    override val EntityImage:Int = R.drawable.bluegren
}
//general
class GeneralDefender(name: String,health: Int,shootingSkill: Int)
    :Defender(name,health,shootingSkill){

    //var ToTheEndSkill:Boolean = false // to save general improvement
    //var AlwaysShootingFirst:Boolean = false//to save general impr
    override val EntityImage:Int = R.drawable.bluegeneral
}