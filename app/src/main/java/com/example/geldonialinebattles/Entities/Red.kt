package com.example.geldonialinebattles.Entities

import com.example.geldonialinebattles.R

class Red(name: String = "Fusilier",health: Int = 1,shootingSkill: Int = 30)
    :Entity(name,health,shootingSkill){

    override val EntityImage:Int = R.drawable.redfis

}

//elite red grenadier
class RedElite(name: String = "Grenadier",health: Int = 1,shootingSkill: Int = 50)
    :Entity(name,health,shootingSkill){

    override val EntityImage:Int = R.drawable.redgren

}
//elite red general
class RedGeneral(name: String = "General",health: Int = 2,shootingSkill: Int = 75)
    :Entity(name,health,shootingSkill){

    override val EntityImage:Int = R.drawable.redgeneral

}