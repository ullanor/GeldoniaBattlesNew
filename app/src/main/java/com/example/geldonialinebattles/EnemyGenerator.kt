package com.example.geldonialinebattles

import com.example.geldonialinebattles.Entities.*
import kotlin.random.Random

class EnemyGenerator {

    // 0 -> redenemy 1 -> orks 2 -> demons
    //var redLoc:ShortArray = shortArrayOf(0,1,2,3,8,9)
    lateinit var battleData:SharedDataClass
    var orcLoc:ShortArray = shortArrayOf(4,5,6)
    var demonLoc:ShortArray = shortArrayOf(7,10,11)//todo difficulty based loc etc.

    fun createRandomEnemies(targetLocation:Short,battleData:SharedDataClass):MutableList<Entity> {
        this.battleData = battleData
        val randomNo = Random.nextInt(0, 2)
        when {
            demonLoc.contains(targetLocation) -> return if(targetLocation == 11.toShort()) demonHard()
            else{
                if(randomNo == 0) {
                    demonEasy()
                }else {
                    demonNormal()
                }

            }
            orcLoc.contains(targetLocation) -> return if(targetLocation == 6.toShort()) orkHard()
            else{
                if(randomNo == 0) {
                    orkEasy()
                }else {
                    orkNormal()
                }

            }
            else -> return if(targetLocation == 3.toShort()) redHard()
            else{
                if(randomNo == 0) {
                    redEasy()
                }else {
                    redNormal()
                }

            }
        }
    }

    //returned enemies band -------------------------------------------------------
    private fun orkEasy():MutableList<Entity>{
        battleData.enemyType = 1
        battleData.battleDifficulty = 'E'
        return mutableListOf(
            EliteOrc(),Orc(), Orc(), Orc(), Orc(),Orc(),Orc(),Orc(),Orc(),Orc()
        )
    }
    private fun orkNormal():MutableList<Entity>{
        battleData.enemyType = 1
        battleData.battleDifficulty = 'N'
        return mutableListOf(
            EliteOrc(), EliteOrc(), EliteOrc(),Orc(),Orc(),Orc(),Orc(),Orc(),Orc()
        )
    }
    private fun orkHard():MutableList<Entity>{
        battleData.enemyType = 1
        battleData.battleDifficulty = 'H'
        battleData.enemyFightToTheEnd = true
        return mutableListOf(
            BossOrc(), EliteOrc(),EliteOrc(), EliteOrc(),EliteOrc(),Orc(),Orc(),Orc(),Orc(),Orc()
        )
    }
    // ---------------------------------- red enemies -------------------------------------
    private fun redEasy():MutableList<Entity>{
        battleData.battleDifficulty = 'E'
        return mutableListOf(
            RedElite(), RedElite(),Red(),Red(),Red(),Red(),Red(),Red()
        )
    }
    private fun redNormal():MutableList<Entity>{
        battleData.battleDifficulty = 'N'
        battleData.enemyHasCannon = true
        return mutableListOf(
            RedElite(), RedElite(),RedElite(),Red(),Red(),Red(),Red(),Red(),Red(),Red())
    }
    private fun redHard():MutableList<Entity>{
        battleData.battleDifficulty = 'H'
        battleData.enemyHasCannon = true
        battleData.enemyFightToTheEnd = true
        return mutableListOf(
            RedGeneral(),RedElite(), RedElite(),RedElite(),RedElite(),Red(),Red(),Red(),Red(),Red())
    }
    // -------------------------------- demons ---------------------------------
    private fun demonEasy():MutableList<Entity>{
        battleData.enemyType = 2
        battleData.battleDifficulty = 'E'
        battleData.enemyFightToTheEnd = true
        return mutableListOf(
            Demon(),Demon(),Demon(),Demon(),Demon(),Demon()
        )
    }
    private fun demonNormal():MutableList<Entity>{
        battleData.enemyType = 2
        battleData.battleDifficulty = 'N'
        battleData.enemyFightToTheEnd = true
        return mutableListOf(
            Demon(),Demon(),Demon(),Demon(),Demon(),Demon(),Demon(),Demon())
    }
    private fun demonHard():MutableList<Entity>{
        battleData.enemyType = 2
        battleData.battleDifficulty = 'H'
        battleData.enemyHasCannon = true
        battleData.enemyFightToTheEnd = true
        return mutableListOf(
            Demon(),Demon(),Demon(),Demon(),Demon(),Demon(),Demon(),Demon(),Demon(),Demon())
    }
}