package com.example.geldonialinebattles

import android.widget.ImageView
import android.widget.Toast
import com.example.geldonialinebattles.Entities.Entity
import com.example.geldonialinebattles.Entities.Orc
import com.example.geldonialinebattles.Entities.RedElite
import com.example.geldonialinebattles.Entities.RedEnemyTest
import kotlin.random.Random

class EnemyGenerator {

    // 0 -> redenemy 1 -> orks 2 -> demons
    var redLoc:ShortArray = shortArrayOf(0,1,2,3,8,9)
    var orcLoc:ShortArray = shortArrayOf(4,5,6,7)
    var demonLoc:ShortArray = shortArrayOf(10,11)//todo difficulty based loc etc.

    fun createRandomEnemies(targetLocation:Short,battleData:SharedDataClass):MutableList<Entity> {
        if(targetLocation == 4.toShort()){//todo orks lcoation test
            battleData.enemyType = 1
            return orkEasy()
        }

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
    private fun orkEasy():MutableList<Entity>{
        return mutableListOf(
            Orc("test1",100,100),
            Orc("test1",100,60),
            Orc("test1",100,90),
            Orc("test1",100,90),
            Orc("test1",100,90),
            Orc("test1",100,90),
            Orc("test1",100,90),
            Orc("test1",100,30)
        )
    }

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