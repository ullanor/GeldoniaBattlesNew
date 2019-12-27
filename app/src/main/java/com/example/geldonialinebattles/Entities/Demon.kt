package com.example.geldonialinebattles.Entities

import com.example.geldonialinebattles.R

class Demon(name: String = "Demon",health: Int = 1,shootingSkill: Int = 50)
    :Entity(name,health,shootingSkill){

    override val EntityImage:Int = R.drawable.demonfis
}