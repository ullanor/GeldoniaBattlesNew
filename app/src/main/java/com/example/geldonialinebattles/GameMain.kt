package com.example.geldonialinebattles

import com.example.geldonialinebattles.Entities.Cannon
import com.example.geldonialinebattles.Entities.Defender
import com.example.geldonialinebattles.Entities.EliteDefender
import com.example.geldonialinebattles.Entities.GeneralDefender

class GameMain {

    var fusiliersCount:Short = 0
    var grenadiersCount:Short = 0
    var generalCount:Short = 0

    fun countDefendersByType(){
        fusiliersCount = 0
        grenadiersCount = 0
        generalCount = 0
        for(defender in PlayerData.defenders){
            when (defender) {
                is EliteDefender -> grenadiersCount++
                is GeneralDefender -> generalCount++
                else -> fusiliersCount++
            }
        }
    }

    //defender shop operations -----------------------------------------
    fun buyFusilier(){
        PlayerData.defenders.add(Defender())
        PlayerData.gold = (PlayerData.gold - 50).toShort()
    }

    fun buyGrenadier(){
        PlayerData.defenders.add(EliteDefender())
        PlayerData.gold = (PlayerData.gold - 300).toShort()
    }

    fun buyGeneral(){
        if(PlayerData.defenders.count() != 0) {
            val tempDef = PlayerData.defenders[0]
            PlayerData.defenders[0] = GeneralDefender()
            PlayerData.defenders.add(tempDef)
        }else
            PlayerData.defenders.add(GeneralDefender())
        PlayerData.gold = (PlayerData.gold - 750).toShort()
    }

    fun buyCannon(){
        PlayerData.defCannon = Cannon("Player Cannon",2,50)
        PlayerData.gold = (PlayerData.gold - 1000).toShort()
    }
    // removing defenders ----------------------------------------
    fun remFusilier(){
        PlayerData.gold = (PlayerData.gold + 25).toShort()
        for(def in PlayerData.defenders){
            if(def !is GeneralDefender && def !is EliteDefender){
                PlayerData.defenders.remove(def)
                break
            }
        }
    }

    fun remGrenadier(){
        PlayerData.gold = (PlayerData.gold + 150).toShort()
        for(def in PlayerData.defenders){
            if(def is EliteDefender){
                PlayerData.defenders.remove(def)
                break
            }
        }
    }

    fun remGeneral(){
        PlayerData.gold = (PlayerData.gold + 375).toShort()
        for(def in PlayerData.defenders){
            if(def is GeneralDefender){
                PlayerData.defenders.remove(def)
                break
            }
        }
    }
    fun remCannon(){
        PlayerData.gold = (PlayerData.gold + 500).toShort()
        PlayerData.defCannon = null
    }

    fun cannonStatus():String{
        return if(PlayerData.defCannon == null)
            "X"+ 0 + " 1000g"
        else "X"+ 1 + " 1000g"
    }
}