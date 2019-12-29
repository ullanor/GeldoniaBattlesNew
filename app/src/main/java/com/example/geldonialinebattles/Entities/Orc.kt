package com.example.geldonialinebattles.Entities

import com.example.geldonialinebattles.R

class Orc(name: String = "Orc",health: Int = 2,shootingSkill: Int = 15)
    :Entity(name,health,shootingSkill){

    override val EntityImage:Int = R.drawable.orcwarrior
}
//orc elite
class EliteOrc(name: String = "Orc Elite",health: Int = 3,shootingSkill: Int = 15)
    :Entity(name,health,shootingSkill){

    override val EntityImage:Int = R.drawable.orcelite
}
//orc boss
class BossOrc(name: String = "Orc Boss",health: Int = 4,shootingSkill: Int = 15)
    :Entity(name,health,shootingSkill){

    override val EntityImage:Int = R.drawable.orcboss
}