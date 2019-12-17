package com.example.geldonialinebattles

import android.widget.ImageView
import com.example.geldonialinebattles.Entities.Defender
import com.example.geldonialinebattles.Entities.EliteDefender
import com.example.geldonialinebattles.Entities.Entity
import kotlin.random.Random

class GameBattle{
    private val enemyGenerator:EnemyGenerator = EnemyGenerator()
    var defenders:MutableList<Entity> = PlayerData.defenders
    var enemies:MutableList<Entity> = mutableListOf()

    var enemiesMoraleBreakPoint:Int = 50
    var defendersMoraleBreakPoint:Int = 50
    var sharedDataClass = SharedDataClass('X')
    var shootingTurnEnd:Boolean = false
    var battleStatus:Char = 'X'
    var battleID:Short = 0

    fun createEnemies(){
        enemies = enemyGenerator.createRandomEnemies(0,sharedDataClass)

        enemiesMoraleBreakPoint = enemies.count()/2
        defendersMoraleBreakPoint = defenders.count()/2
    }

    //fun createTestDefenders(){
    //    defenders = mutableListOf(
    //        EliteDefender("Elite",100,90,888),
    //        Defender("D2",100,40,0),
    //        Defender("D2",100,40,0),
    //        Defender("D2",100,40,0),
    //        Defender("D2",100,40,0),
    //        Defender("D2",100,40,0),
    //        Defender("testdef5",100,50,0)
    //        )
    //}

    //player is shooting
    fun playerIsShooting():String{
        var hitCounter:Short = 0
        var deadCounter:Short = 0
        var enemy:Entity

        for(x in 0 until defenders.count()){
            var hits:Int = Random.nextInt(1,101)
            if(hits > defenders[x].shootingSkill)
                continue
            hitCounter++
            enemy = enemies[Random.nextInt(enemies.count())]
            if(enemy.AssessDamage()){
                deadCounter++
                enemies.remove(enemy)
            }

            if(enemies.count() <= 0) {
                battleStatus = 'V'
                return "Hitted: $hitCounter Killed: $deadCounter\nNo enemies left! Victory!"
            }
        }

        return "Hitted: $hitCounter Killed: $deadCounter"
    }

    //enemy is shooting
    fun enemyIsShooting():String{
        var hitCounter:Short = 0
        var deadCounter:Short = 0
        var defender:Entity

        for(x in 0 until enemies.count()){
            var hits:Int = Random.nextInt(1,101)
            if(hits > enemies[x].shootingSkill)
                continue
            hitCounter++
            defender = defenders[Random.nextInt(defenders.count())]
            if(defender.AssessDamage()){
                deadCounter++
                defenders.remove(defender)
            }

            if(defenders.count() <= 0) {
                battleStatus = 'D'
                return "Hitted: $hitCounter Killed: $deadCounter\nNo defenders left! Defeat!"
            }
        }

        return "Hitted: $hitCounter Killed: $deadCounter"
    }


}