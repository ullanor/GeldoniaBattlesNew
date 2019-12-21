package com.example.geldonialinebattles

import com.example.geldonialinebattles.Entities.Cannon
import com.example.geldonialinebattles.Entities.Defender
import com.example.geldonialinebattles.Entities.EliteDefender

class GameMain {

    var fusiliersCount:Short = 0
    var grenadiersCount:Short = 0

    fun countDefendersByType(){
        fusiliersCount = 0
        grenadiersCount = 0
        for(defender in PlayerData.defenders){
            if(defender is EliteDefender)
                grenadiersCount++
            else fusiliersCount++
        }
    }

    fun createTestDefenders(){
        val testDefenders = mutableListOf(
            EliteDefender("Elite",100,90,888),
            Defender("D2",100,40,0),
            Defender("D2",100,40,0),
            Defender("D2",100,40,0),
            Defender("D2",100,40,0),
            Defender("D2",100,40,0),
            Defender("testdef5",100,50,0)
        )
        PlayerData.defenders.addAll(testDefenders)
    }

    //defender shop operations -----------------------------------------
    fun buyFusilier(){
        PlayerData.defenders.add(
            Defender("Fusilier",100,10,888))
        PlayerData.gold = (PlayerData.gold - 50).toShort()
    }

    fun buyGrenadier(){
        PlayerData.defenders.add(
            EliteDefender("Elite",120,90,0))
        PlayerData.gold = (PlayerData.gold - 300).toShort()
    }

    fun buyCannon(){
        PlayerData.defCannon = Cannon("howitzer",100,80)
        PlayerData.gold = (PlayerData.gold - 1000).toShort()
    }

    fun cannonStatus():String{
        return if(PlayerData.defCannon == null)
            "X"+ 0 + " 1000g"
        else "X"+ 1 + " 1000g"
    }
}