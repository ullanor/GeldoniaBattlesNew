package com.example.geldonialinebattles.Entities

import com.example.geldonialinebattles.R

class Orc(name: String,health: Int,shootingSkill: Int)
    :Entity(name,health,shootingSkill){

    override val EntityImage:Int = R.drawable.orcwarrior
}