package com.example.geldonialinebattles

import android.content.EntityIterator
import android.widget.ImageView
import android.widget.Toast
import com.example.geldonialinebattles.Entities.Cannon
import com.example.geldonialinebattles.Entities.Defender
import com.example.geldonialinebattles.Entities.EliteDefender
import com.example.geldonialinebattles.Entities.Entity
import kotlin.random.Random

class GameBattle{
    private val enemyGenerator:EnemyGenerator = EnemyGenerator()
    var defenders:MutableList<Entity> = PlayerData.defenders
    var enemies:MutableList<Entity> = mutableListOf()
    var enemyCannon:Cannon? = null
    //var defCannon:Cannon? = PlayerData.defCannon

    var enemiesMoraleBreakPoint:Int = 50
    var defendersMoraleBreakPoint:Int = 50
    var sharedDataClass = SharedDataClass('X',false)
    var shootingTurnEnd:Boolean = false
    var battleStatus:Char = 'X'
    //var battleID:Short = 0
    var defDeadNo:MutableList<Int> = mutableListOf() //list of dead bodies
    var eneDeadNo:MutableList<Int> = mutableListOf()

    var defCannonPosition = 0 //rotate player cannon - 3 positions!


    fun createEnemies(){
        enemies = enemyGenerator.createRandomEnemies(0,sharedDataClass)

        enemiesMoraleBreakPoint = enemies.count()/2
        defendersMoraleBreakPoint = defenders.count()/2
    }

    fun createEnemyCannon(){
        if(!sharedDataClass.enemyHasCannon)
            return
        enemyCannon = Cannon("cannon",100,80)

    }

    fun victoryMoney():Short{
        val diff = sharedDataClass.battleDifficulty
        if(diff == 'E')
            return 300
        else if(diff == 'N')
            return 1000
        else return 0
    }

    //player is shooting
    fun playerIsShooting():String{
        var cannonString:String = ""
        var hitCounter:Short = 0
        var deadCounter:Short = 0
        var enemy:Entity
        var hits:Int

        //cannon shooting -----------------------------------------------------------
        if(PlayerData.defCannon != null) {
            hits = Random.nextInt(1, 101)
            if (hits <= PlayerData.defCannon!!.shootingSkill) {
                cannonString = "\nCannon hit! "
                cannonString += playerCannonHitCheck(Random.nextInt(0, 3))

            }
            else cannonString = "\nCannon missed!"
            if(enemies.count() <= 0) {
                battleStatus = 'V'
                return "$cannonString\nNo enemies left! Victory!"
            }

        }

        // def troops ----------------------------------------------------------------------
        for(x in 0 until defenders.count()){
            hits = Random.nextInt(1,101)
            if(hits > defenders[x].shootingSkill)
                continue
            hitCounter++
            enemy = enemies[Random.nextInt(enemies.count())]
            if(enemy.AssessDamage()){
                eneDeadNo.add(enemy.myGamePictueNo)
                deadCounter++
                enemies.remove(enemy)
            }

            if(enemies.count() <= 0) {
                battleStatus = 'V'
                return "Hitted: $hitCounter Killed: $deadCounter $cannonString\nNo enemies left! Victory!"
            }
        }

        return "Hitted: $hitCounter Killed: $deadCounter $cannonString"
    }

    private fun playerCannonHitCheck(rand:Int):String{
        var dead = 0
        var pictureHitByCannon:IntArray
        if(defCannonPosition == 2){
            if(rand == 0)
                pictureHitByCannon = intArrayOf(0,5)
            else pictureHitByCannon = intArrayOf(1,6)
        }
        else if(defCannonPosition == 1){
            if(rand == 0)
                pictureHitByCannon = intArrayOf(2,7)
            else if(rand == 1)
                pictureHitByCannon = intArrayOf(3,8)
            else pictureHitByCannon = intArrayOf(4,9)
        }
        else pictureHitByCannon = intArrayOf(66)

        if(pictureHitByCannon.size == 1) {//-> enemy cannon get hit!
            if(enemyCannon != null) {
                if(enemyCannon!!.AssessDamage())
                    enemyCannon = null
                return " Enemy cannon!"
            }else return " Almost!"
        }
        for(number in pictureHitByCannon){
            for(enemy in enemies) {
                if(enemy.myGamePictueNo == number) {
                    eneDeadNo.add(enemy.myGamePictueNo)
                    dead++
                    enemies.remove(enemy)
                    break
                }
            }
        }
        return " Killed: $dead"
    }

    //enemy is shooting + now with cannon! ---------------------------------------------------------------------------------------------
    fun enemyIsShooting():String{
        var cannonString:String = ""
        var hitCounter:Short = 0
        var deadCounter:Short = 0
        var defender:Entity
        var hits: Int

        //cannon shooting ---------------------------------------------
        if(enemyCannon != null) {
            hits = Random.nextInt(1, 101)
            if (hits <= enemyCannon!!.shootingSkill) {
                //hitCounter++
                cannonString = "\nCannon hit! "
                cannonString += enemyCannonHitCheck(Random.nextInt(0, 3))

                }
                else cannonString = "\nCannon missed!"
                if(defenders.count() <= 0) {
                    battleStatus = 'D'
                    return "$cannonString\nNo defenders left! Defeat!"
                }

        }

        //troops ------------------------------------------------------------
        for(x in 0 until enemies.count()){
            hits = Random.nextInt(1,101)
            if(hits > enemies[x].shootingSkill)
                continue
            hitCounter++
            defender = defenders[Random.nextInt(defenders.count())]
            if(defender.AssessDamage()){
                defDeadNo.add(defender.myGamePictueNo)
                deadCounter++
                defenders.remove(defender)
            }

            if(defenders.count() <= 0) {
                battleStatus = 'D'
                return "Hitted: $hitCounter Killed: $deadCounter $cannonString\nNo defenders left! Defeat!"
            }
        }

        return "Hitted: $hitCounter Killed: $deadCounter $cannonString"
    }

    private fun enemyCannonHitCheck(rand:Int):String{ //return hitted pictures! -------------------------------------------------------
        var dead = 0
        //var rand = 2
        var pictureHitByCannon:IntArray
        var newRand:Int = Random.nextInt(0,3)
        if(rand == 0){
            if(newRand == 0)
                pictureHitByCannon = intArrayOf(0,5)
            else pictureHitByCannon = intArrayOf(1,6)
        }
        else if(rand == 1){
            if(newRand == 0)
                pictureHitByCannon = intArrayOf(2,7)
            else if(newRand == 1)
                pictureHitByCannon = intArrayOf(3,8)
            else pictureHitByCannon = intArrayOf(4,9)
        }
        else pictureHitByCannon = intArrayOf(66)

        if(pictureHitByCannon.size == 1) {//-> player cannon get hit!
            if(PlayerData.defCannon != null) {
                if(PlayerData.defCannon!!.AssessDamage())
                    PlayerData.defCannon = null
                return " Our cannon!"
            }else return " That was close!"
        }
        for(number in pictureHitByCannon){
            for(defender in defenders) {
                if(defender.myGamePictueNo == number) {
                    defDeadNo.add(defender.myGamePictueNo)
                    dead++
                    defenders.remove(defender)
                    break
                }
            }
        }
        return " Killed: $dead"
    }

}