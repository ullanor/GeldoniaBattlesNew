package com.example.geldonialinebattles

import android.widget.ImageView
import android.widget.Toast
import com.example.geldonialinebattles.Entities.Entity
import com.example.geldonialinebattles.Entities.RedElite
import com.example.geldonialinebattles.Entities.RedEnemyTest
import kotlin.random.Random

class EnemyGenerator {

    var redLoc = arrayOf(0,1,2)
    var orcLoc = arrayOf(3,4)

    fun createRandomEnemies(targetLocation:Int,battleData:SharedDataClass):MutableList<Entity> {
        val randomNo = Random.nextInt(0, 4)
        return if(randomNo < 2) {
            battleData.battleDifficulty = 'E'
            redEasy()
        } else {
            battleData.battleDifficulty = 'N'
            redNormal()
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