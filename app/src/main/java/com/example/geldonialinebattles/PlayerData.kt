package com.example.geldonialinebattles

import com.example.geldonialinebattles.Entities.EliteDefender
import com.example.geldonialinebattles.Entities.Entity
import java.io.Serializable
import java.lang.StringBuilder

class PlayerData{
    companion object{
        var testString:String = "JOWISZ 2"
        var defenders:MutableList<Entity> =
            mutableListOf(EliteDefender("General",100,90,878))
        var gold:Short = 5000
        var locationToAttack:Short = 0
    }
}

data class SharedDataClass(var battleDifficulty:Char)

enum class BattleLocation(val location:Short){
    none(0),
    desert(1),
    jungle(2);

    companion object {
        private val values = values()
        fun getByValue(location: Short) = values.firstOrNull { it.location == location }
    }
}

//for saving
data class GeldoniaSaveData(val gold : Short,val defenders : MutableList<Entity>,
                            val locationToAttack: Short) : Serializable