package com.example.geldonialinebattles


import android.content.Context
import android.content.Intent
import android.graphics.PorterDuffXfermode
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.geldonialinebattles.Entities.Defender
import kotlinx.android.synthetic.main.activity_battle.*
import java.lang.Exception

class BattleActivity : AppCompatActivity() {
    lateinit var battle:GameBattle
    lateinit var timer: CountDownTimer
    var enemiesPictures: Array<ImageView> = arrayOf()
    var defendersPictures:Array<ImageView> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        enemiesPictures = GetEnemiesPictures()
        defendersPictures = GetDefendersPictures()


        battle = GameBattle()
        GetBattleName()
        SetButtons()
        HideAllUIElements()
        StartBattle()
        SetCloudSize()
    }

    private fun GetEnemiesPictures():Array<ImageView>{
        return arrayOf(ene1,ene2,ene3,ene4,ene5,ene6,ene7,ene8,ene9,ene10)}
    private fun GetDefendersPictures():Array<ImageView>{
        return arrayOf(def1,def2,def3,def4,def5,def6,def7,def8,def9,def10)}


    private fun GetBattleName(){
        //battle.battleID = (intent.getIntExtra("battleID",0)).toShort()
        Toast.makeText(this@BattleActivity, "Battle of " +
                BattleLocation.getByValue(PlayerData.locationToAttack), Toast.LENGTH_SHORT).show()

    }
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
    }

    private fun SetCloudSize(){ //todo cannon extra size
        val defCount:Int = battle.defenders.count()
        val eneCount:Int = battle.enemies.count()
        //Toast.makeText(this@BattleActivity,"$defCount $eneCount",Toast.LENGTH_SHORT).show()
        if(defCount < 5) {
            defCloud.layoutParams.height = resources.getDimension(R.dimen.height_less).toInt()
        }
        else {
            defCloud.layoutParams.height = resources.getDimension(R.dimen.height_normal).toInt()
        }
        if(eneCount < 5)
            eneCloud.layoutParams.height = resources.getDimension(R.dimen.height_less).toInt()
        else eneCloud.layoutParams.height = resources.getDimension(R.dimen.height_normal).toInt()

        Toast.makeText(this@BattleActivity,
            defCloud.layoutParams.height.toString() + " " + defCount + " "+
            eneCloud.layoutParams.height.toString() + " " + eneCount,Toast.LENGTH_LONG).show()

    }


    //battle preparations! UI and units -------------------------------------------
    private fun StartBattle(){
        MainGrid.setBackgroundResource(R.drawable.mapplain)

        battle.createEnemies()
        UpdateEntitiesLocalization()
    }

    private fun UpdateEntitiesDeadImage(){
        for(deadNo in battle.defDeadNo){
            defendersPictures[deadNo].setImageResource(R.drawable.bluedead)
        }
        for(deadNo in battle.eneDeadNo){
            enemiesPictures[deadNo].setImageResource(R.drawable.reddead)
        }
    }

    private fun UpdateEntitiesLocalization(){
        for(x in 0 until battle.defenders.count()){
            battle.defenders[x].myGamePictueNo = x
            defendersPictures[x].setImageResource(battle.defenders[x].EntityImage)
            defendersPictures[x].visibility = View.VISIBLE
        }
        for(x in 0 until battle.enemies.count()){
            battle.enemies[x].myGamePictueNo = x
            enemiesPictures[x].setImageResource(battle.enemies[x].EntityImage)
            enemiesPictures[x].visibility = View.VISIBLE
        }
    }

    // ------------------------------------------ buttons ---------------------------------
    private fun SetButtons(){
        fireButton.setOnClickListener{
            //Toast.makeText(this@MainActivity, Random.nextInt(0, 4).toString(), Toast.LENGTH_SHORT).show()
            try {
                //battle.shootingTurnEnd = false //must reset that state !
                if(CheckBattleIsActiveStatus())
                    PerformShooting()
                else{
                    Toast.makeText(this@BattleActivity, "THE END", Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                Toast.makeText(this@BattleActivity, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        moveButton.setOnClickListener{
            if(CheckBattleIsActiveStatus()) {
                val test:Int = (battle.defenders[0] as Defender).expPoints
                Toast.makeText(this@BattleActivity, "Battle is not finished! $test", Toast.LENGTH_SHORT).show()
            }
            else{
                //battle.battleStatus = 'X' //must reset battle state !
                //HideAllUIElements()
                //StartBattle()

                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun CheckBattleIsActiveStatus():Boolean{
        battle.shootingTurnEnd = false //must reset that state !
        val status:Char = battle.battleStatus
        return when (status) {
            'V' -> false //todo victory and defeat after battle effects ;)
            'D' -> false
            else -> true
        }
    }

    // --------------------------------------------- shoooting --------------------------
    private fun PerformShooting(){
        //battleText.text = battle.enemies.count().toString()
        Toast.makeText(this@BattleActivity, battle.enemies.count().toString(), Toast.LENGTH_SHORT).show()
        //battle.shootingTurnEnd = false
        ShootingCloud(true)
        //battleText.text = battle.playerIsShooting()
        //ShootingCloud(false)
    }

    private fun ShootingCloud(isPlayer:Boolean){
        if(!CheckBelligerentsCount())
            return

        var counter:Short= 0
        SetBattleMenuVis(false)
        if(isPlayer) {
            defCloud.visibility = View.VISIBLE
            battleText.text = battle.playerIsShooting()
            UpdateEntitiesDeadImage()
        }
        else {
            eneCloud.visibility = View.VISIBLE
            battleText.text = battle.enemyIsShooting()
            UpdateEntitiesDeadImage()
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
                if(battle.battleStatus != 'D')
                    battleText.text = "Keep Fighting!"
                if(isPlayer) {
                    defCloud.visibility = View.INVISIBLE
                    defCloud.setImageResource(R.drawable.firingcloud)
                    if(!battle.shootingTurnEnd) {
                        ShootingCloud(false)
                        battle.shootingTurnEnd = true
                    }else SetBattleMenuVis(true)
                }else{
                    eneCloud.visibility = View.INVISIBLE
                    eneCloud.setImageResource(R.drawable.firingcloud)
                    if(!battle.shootingTurnEnd) {
                        ShootingCloud(true)
                        battle.shootingTurnEnd = true
                    }else SetBattleMenuVis(true)
                }
            }
        }
        timer.start()
    }

    private fun CheckBelligerentsCount():Boolean{
        //todo check belligerents morale test! + check count -> make cloud size (height)
        return when {
            battle.defenders.count() == 0 -> {
                battleText.text = "We have lost the battle!"
                SetBattleMenuVis(true)
                battle.battleStatus = 'D'
                false
            }
            battle.enemies.count() == 0 -> {
                battleText.text = "The battle is victorious!"
                SetBattleMenuVis(true)
                battle.battleStatus = 'V'
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
