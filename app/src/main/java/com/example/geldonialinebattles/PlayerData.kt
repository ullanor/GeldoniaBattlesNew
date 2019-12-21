package com.example.geldonialinebattles

import com.example.geldonialinebattles.Entities.Cannon
import com.example.geldonialinebattles.Entities.EliteDefender
import com.example.geldonialinebattles.Entities.Entity
import java.io.Serializable
import java.lang.StringBuilder

class PlayerData{
    companion object{
        var testString:String = "JOWISZ 2"
        var gameOver:Boolean = false

        //to save --------------
        var defenders:MutableList<Entity> =
            mutableListOf(EliteDefender("General",100,90,878))
        var gold:Short = 5000
        var locationToAttack:Short = 0
        var defCannon:Cannon? = null
    }
}

//for saving ---------------------------------------------------------------------
data class GeldoniaSaveData(val gold : Short,val defenders : MutableList<Entity>,
                            val locationToAttack: Short,val defCannon: Cannon?) : Serializable