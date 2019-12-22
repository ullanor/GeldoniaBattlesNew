package com.example.geldonialinebattles

import android.widget.ImageView
import android.widget.Toast
import com.example.geldonialinebattles.Entities.Entity
import com.example.geldonialinebattles.Entities.RedElite
import com.example.geldonialinebattles.Entities.RedEnemyTest
import kotlin.random.Random

class EnemyGenerator {

    var redLoc:ShortArray = shortArrayOf(0,1,2,3,8,9)
    var orcLoc:ShortArray = shortArrayOf(4,5,6,7)
    var demonLoc:ShortArray = shortArrayOf(10,11)//todo difficulty based loc etc.

    fun createRandomEnemies(targetLocation:Int,battleData:SharedDataClass):MutableList<Entity> {
        val randomNo = Random.nextInt(0, 3)
        if(randomNo == 0){
            battleData.battleDifficulty = 'E'
            battleData.enemyFightToTheEnd = true
            return redEasy()
        } else if(randomNo == 1){
            battleData.battleDifficulty = 'E'
            return redEasy()
        }
        else {
            battleData.battleDifficulty = 'N'
            battleData.enemyHasCannon = true
            return redNormal()
        }
    }

    //test enemies
    private fun redEasy():MutableList<Entity>{
        return mutableListOf(
            RedEnemyTest("test1",100,30),
            RedEnemyTest("test2",100,30),
            RedEnemyTest("test2",100,30),
            RedElite("elite",120,80),
            RedEnemyTest("test2",100,90))
    }
    private fun redNormal():MutableList<Entity>{
        return mutableListOf(
            RedEnemyTest("test1",100,30),
            RedElite("elite",120,60),
            RedElite("elite",120,40),
            RedEnemyTest("test2",100,30),
            RedEnemyTest("test2",100,30),
            RedEnemyTest("test2",100,30),
            RedEnemyTest("test2",100,30),
            RedEnemyTest("test2",100,30),
            RedEnemyTest("test2",100,30),
            RedEnemyTest("test2",100,30))
    }

}