package com.example.geldonialinebattles

import android.graphics.drawable.Drawable
import android.media.Image
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.view.isVisible
import com.example.geldonialinebattles.Entities.Defender
import com.example.geldonialinebattles.Entities.Entity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var game:Game
    lateinit var timer: CountDownTimer
    var enemiesPictures: Array<ImageView> = arrayOf()
    var defendersPictures:Array<ImageView> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enemiesPictures = GetEnemiesPictures()
        defendersPictures = GetDefendersPictures()

        game = Game()
        SetButtons()
        HideAllUIElements()
        StartBattle()

    }

    private fun GetEnemiesPictures():Array<ImageView>{
        return arrayOf(ene1,ene2,ene3,ene4,ene5,ene6,ene7,ene8,ene9,ene10)}
    private fun GetDefendersPictures():Array<ImageView>{
        return arrayOf(def1,def2,def3,def4,def5,def6,def7,def8,def9,def10)}


    private fun HideAllUIElements(){
        def1.visibility = View.INVISIBLE
        def2.visibility = View.INVISIBLE
        def3.visibility = View.INVISIBLE
        def4.visibility = View.INVISIBLE
        def5.visibility = View.INVISIBLE

        def6.visibility = View.INVISIBLE
        def7.visibility = View.INVISIBLE
        def8.visibility = View.INVISIBLE
        def9.visibility = View.INVISIBLE
        def10.visibility = View.INVISIBLE
        //defBack.isVisible = false
        //defFront.isVisible = false
        //eneFront.isVisible = false
        ene1.visibility = View.INVISIBLE
        ene2.visibility = View.INVISIBLE
        ene3.visibility = View.INVISIBLE
        ene4.visibility = View.INVISIBLE
        ene5.visibility = View.INVISIBLE

        //eneBack.isVisible = false
        ene6.visibility = View.INVISIBLE
        ene7.visibility = View.INVISIBLE
        ene8.visibility = View.INVISIBLE
        ene9.visibility = View.INVISIBLE
        ene10.visibility = View.INVISIBLE


        defCannon.visibility = View.VISIBLE
        eneCannon.visibility = View.VISIBLE//todo just for testing
        defCloud.visibility = View.INVISIBLE
        eneCloud.visibility = View.INVISIBLE
        //fireButton.isVisible = false
        //retreatButton.isVisible = false
    }
    //normally after start clicked - for testing!
    private fun StartBattle(){
        if(game.defenders.count() < 1){
            //Toast.makeText(this@MainActivity, "We need more man to fight!", Toast.LENGTH_SHORT).show()
            //return
        }

        //fireButton.isVisible = true
        //moveButton.isVisible = true
        MainGrid.setBackgroundResource(R.drawable.mapplain)

        game.createEnemies()
        Toast.makeText(this@MainActivity, game.sharedDataClass.battleDifficulty.toString(), Toast.LENGTH_SHORT).show()
        game.createTestDefenders() //testing
        UpdateEntitiesLocalization()
    }

    private fun UpdateEntitiesLocalization(){
        //var test:String = ""
        for(x in 0 until game.defenders.count()){
            game.defenders[x].SelectNewPicture(defendersPictures[x])
            //test += game.defenders[x].myGamePicture.id.toString() + "\n"
        }
        for(x in 0 until game.enemies.count()){
            game.enemies[x].SelectNewPicture(enemiesPictures[x])
            //test += game.defenders[x].myGamePicture.id.toString() + "\n"
        }
        //gameText.text = SpannableStringBuilder(test)
        ShowEntitiesOnBattleField()
    }

    private fun ShowEntitiesOnBattleField(){
        for(defender in game.defenders){
            defender.ShowMeOnField()
        }
        for(enemy in game.enemies)
            enemy.ShowMeOnField()
    }


    // ------------------------------------------ buttons ---------------------------------
    private fun SetButtons(){
        fireButton.setOnClickListener{
            //Toast.makeText(this@MainActivity, Random.nextInt(0, 4).toString(), Toast.LENGTH_SHORT).show()
            try {
                //game.shootingTurnEnd = false //must reset that state !
                if(CheckBattleIsActiveStatus())
                    PerformShooting()
                else{
                    Toast.makeText(this@MainActivity, "THE END", Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                Toast.makeText(this@MainActivity, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        moveButton.setOnClickListener{
            if(CheckBattleIsActiveStatus()) {
                val test:Int = (game.defenders[0] as Defender).expPoints
                Toast.makeText(this@MainActivity, "Battle is not finished! $test", Toast.LENGTH_SHORT).show()
            }
            else{
                game.battleStatus = 'X' //must reset battle state !
                HideAllUIElements()
                StartBattle()
            }
        }
    }

    private fun CheckBattleIsActiveStatus():Boolean{
        game.shootingTurnEnd = false //must reset that state !
        val status:Char = game.battleStatus
        return when (status) {
            'V' -> false //todo victory and defeat after battle effects ;)
            'D' -> false
            else -> true
        }
    }

    // --------------------------------------------- shoooting --------------------------
    private fun PerformShooting(){
        //gameText.text = game.enemies.count().toString()
        Toast.makeText(this@MainActivity, game.enemies.count().toString(), Toast.LENGTH_SHORT).show()
        //game.shootingTurnEnd = false
        ShootingCloud(true)
        //gameText.text = game.playerIsShooting()
        //ShootingCloud(false)
    }

    private fun ShootingCloud(isPlayer:Boolean){
        if(!CheckBelligerentsCount())
            return

        var counter:Short= 0
        SetBattleMenuVis(false)
        if(isPlayer) {
            defCloud.visibility = View.VISIBLE
            gameText.text = game.playerIsShooting()
        }
        else {
            eneCloud.visibility = View.VISIBLE
            gameText.text = game.enemyIsShooting()
        }

        timer = object: CountDownTimer(2000, 500) {
            override fun onTick(millisUntilFinished: Long) {
                counter++
                if(counter == 2.toShort()) {
                    if(isPlayer)
                        defCloud.setImageResource(R.drawable.firingcloud1)
                    else eneCloud.setImageResource(R.drawable.firingcloud1)
                }
                else if(counter == 3.toShort()) {
                    if(isPlayer)
                        defCloud.setImageResource(R.drawable.firingcloud2)
                    else eneCloud.setImageResource(R.drawable.firingcloud2)
                }
            }

            override fun onFinish() {
                timer.cancel()
                if(game.battleStatus != 'D')
                    gameText.text = "Keep Fighting!"
                if(isPlayer) {
                    defCloud.visibility = View.INVISIBLE
                    defCloud.setImageResource(R.drawable.firingcloud)
                    if(!game.shootingTurnEnd) {
                        ShootingCloud(false)
                        game.shootingTurnEnd = true
                    }else SetBattleMenuVis(true)
                }else{
                    eneCloud.visibility = View.INVISIBLE
                    eneCloud.setImageResource(R.drawable.firingcloud)
                    if(!game.shootingTurnEnd) {
                        ShootingCloud(true)
                        game.shootingTurnEnd = true
                    }else SetBattleMenuVis(true)
                }
            }
        }
        timer.start()
    }

    private fun CheckBelligerentsCount():Boolean{ //todo check belligerents morale test!
        return when {
            game.defenders.count() == 0 -> {
                gameText.text = "We have lost the battle!"
                SetBattleMenuVis(true)
                game.battleStatus = 'D'
                false
            }
            game.enemies.count() == 0 -> {
                gameText.text = "The battle is victorious!"
                SetBattleMenuVis(true)
                game.battleStatus = 'V'
                false
            }
            else -> true
        }
    }

    private fun SetBattleMenuVis(isVisible:Boolean){
        if(isVisible){
            fireButton.visibility = View.VISIBLE
            moveButton.visibility =View.VISIBLE
        }else{
            fireButton.visibility = View.INVISIBLE
            moveButton.visibility =View.INVISIBLE
        }
    }
}
