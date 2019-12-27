package com.example.geldonialinebattles.Entities

import com.example.geldonialinebattles.R

//defenders
open class Defender(name: String = "Fusilier", health: Int = 1, shootingSkill: Int = 30)
    :Entity(name,health,shootingSkill){

    override val EntityImage:Int = R.drawable.bluefis
}
//elite
class EliteDefender(name: String = "Grenadier",health: Int = 1,shootingSkill: Int = 50)
    :Defender(name,health,shootingSkill){

    override val EntityImage:Int = R.drawable.bluegren
}
//general
class GeneralDefender(name: String = "General",health: Int = 2,shootingSkill: Int = 75)
    :Defender(name,health,shootingSkill){

    override val EntityImage:Int = R.drawable.bluegeneral
}