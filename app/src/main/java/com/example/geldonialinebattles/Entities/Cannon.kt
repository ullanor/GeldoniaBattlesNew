package com.example.geldonialinebattles.Entities

class Cannon(val name:String, var health:Int,
             var shootingSkill: Int) : java.io.Serializable {
    fun AssessDamage():Boolean{
        health -= 1
        if(health <= 0){
            return true
        }
        return false
    }
}
