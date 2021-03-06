package com.example.geldonialinebattles

import com.example.geldonialinebattles.Entities.Cannon
import com.example.geldonialinebattles.Entities.EliteDefender
import com.example.geldonialinebattles.Entities.Entity
import java.io.Serializable

class PlayerData{
    companion object{
        var playerLocIsAttacked = false

        //to save -------------- player abilities
        var playerEXP:Short = 0
        var trainedCrew:Boolean = false
        var steadFast:Boolean = false
        var quickShooter:Boolean = false
        //to save -------------- other
        var playerLocations:MutableList<Short> = mutableListOf(0)
        var defenders:MutableList<Entity> =
            mutableListOf(EliteDefender())
        var gold:Short = 1500
        var locationToAttack:Short = 66
        var defCannon:Cannon? = null
    }
}

//for saving ---------------------------------------------------------------------
data class GeldoniaSaveData(val gold : Short,
                            val defenders : MutableList<Entity>,
                            val locationToAttack: Short,
                            val defCannon: Cannon?,
                            val playerLocations:MutableList<Short>,
                            val playerExp:Short,
                            val trainedCrew:Boolean,
                            val steadFast:Boolean,
                            val quickShooter:Boolean) : Serializable