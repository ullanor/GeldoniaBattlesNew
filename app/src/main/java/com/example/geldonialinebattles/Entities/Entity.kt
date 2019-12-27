package com.example.geldonialinebattles.Entities

import com.example.geldonialinebattles.R
import java.io.Serializable

abstract class Entity(val name:String, var health:Int,
                     val shootingSkill:Int) : Serializable{
    var myGamePictueNo: Int = 0

    fun AssessDamage():Boolean{
        health -= 1
        if(health <= 0){
            return true
        }
        return false
    }

    open val EntityImage:Int = R.drawable.redfis
}