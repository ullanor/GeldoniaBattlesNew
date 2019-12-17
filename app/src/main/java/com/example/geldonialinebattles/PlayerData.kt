package com.example.geldonialinebattles

import com.example.geldonialinebattles.Entities.EliteDefender
import com.example.geldonialinebattles.Entities.Entity

class PlayerData {
    companion object{
        var testString:String = "JOWISZ 2"
        var defenders:MutableList<Entity> =
            mutableListOf(EliteDefender("General",100,90,878))
        var gold:Short = 5000
    }
}

data class SharedDataClass(var battleDifficulty:Char)