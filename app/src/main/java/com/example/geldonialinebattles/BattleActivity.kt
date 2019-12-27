package com.example.geldonialinebattles


import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_battle.*
import java.lang.Exception
import kotlin.random.Random


class BattleActivity : AppCompatActivity() {
    lateinit var battle:GameBattle
    lateinit var timer: CountDownTimer
    var enemiesPictures: Array<ImageView> = arrayOf()
    var defendersPictures:Array<ImageView> = arrayOf()
    var enemyType:Short = 0
    lateinit var battleSound:SoundPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        enemiesPictures = GetEnemiesPictures()
        defendersPictures = GetDefendersPictures()
        battleSound = SoundPlayer(this)

        battle = GameBattle()
        SetButtons()
        HideAllUIElements()
        StartBattle()
        SetCloudSize()
        GetNameMapAndDiff()
    }

    private fun GetEnemiesPictures():Array<ImageView>{
        return arrayOf(ene1,ene2,ene3,ene4,ene5,ene6,ene7,ene8,ene9,ene10)}
    private fun GetDefendersPictures():Array<ImageView>{
        return arrayOf(def1,def2,def3,def4,def5,def6,def7,def8,def9,def10)}


    private fun GetNameMapAndDiff(){
        val enumNo = BattleLocation.getByValue(PlayerData.locationToAttack)
        MainGrid.setBackgroundResource(BattleLocationMap.valueOf(enumNo.toString()).mapLoc)
        //battleDifficultyText.text = "EneType: $enemyType ${PlayerData.locationToAttack}"
        battleDifficultyText.text = "Difficulty: ${battle.sharedDataClass.battleDifficulty}"
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


        defCannon.visibility = View.INVISIBLE
        if(PlayerData.defCannon == null)
            cannonButton.visibility = View.INVISIBLE
        eneCannon.visibility = View.INVISIBLE//todo just for testing
        defCloud.visibility = View.INVISIBLE
        eneCloud.visibility = View.INVISIBLE
    }

    private fun SetCloudSize(){ //todo cannon extra size and player to the end ;)
        val isTabletDev = isTablet(this)
        //Toast.makeText(this@BattleActivity,isTabletDev.toString(),Toast.LENGTH_SHORT).show()
        //-----------------------------------------------------------------------------------+++
        val test = battle.defAlwaysShootingFirst.toString() + " SF: " +battle.defToTheDeath + " TC: " + PlayerData.defCannon?.shootingSkill
        Toast.makeText(this@BattleActivity,
            "E: "+battle.sharedDataClass.enemyFightToTheEnd+" QS: "+ test,Toast.LENGTH_LONG).show()
        //-----------------------------------------------------------------------------------+++
        val defCount:Int = PlayerData.defenders.count()
        val eneCount:Int = battle.enemies.count()

        //defenders --------------------------------------------------------------
        if(PlayerData.defCannon != null){
            if(isTabletDev)
                defCloud.layoutParams.height = resources.getDimension(R.dimen.height_Tcannon).toInt()
            else defCloud.layoutParams.height = resources.getDimension(R.dimen.height_cannon).toInt()
        }else {
            if (defCount < 5) {
                if (isTabletDev)
                    defCloud.layoutParams.height =
                        resources.getDimension(R.dimen.height_Tless).toInt()
                else
                    defCloud.layoutParams.height =
                        resources.getDimension(R.dimen.height_less).toInt()
            } else {
                if (isTabletDev)
                    defCloud.layoutParams.height =
                        resources.getDimension(R.dimen.height_Tnormal).toInt()
                else
                    defCloud.layoutParams.height =
                        resources.getDimension(R.dimen.height_normal).toInt()
            }
        }

        //enemies -----------------------------------------------------
        if(battle.sharedDataClass.enemyHasCannon){
            if(isTabletDev)
                eneCloud.layoutParams.height = resources.getDimension(R.dimen.height_Tcannon).toInt()
            else
                eneCloud.layoutParams.height = resources.getDimension(R.dimen.height_cannon).toInt()
            return
        }

        if(eneCount < 5) {
            if(isTabletDev)
                eneCloud.layoutParams.height = resources.getDimension(R.dimen.height_Tless).toInt()
            else
                eneCloud.layoutParams.height = resources.getDimension(R.dimen.height_less).toInt()
        }
        else {
            if(isTabletDev)
                eneCloud.layoutParams.height = resources.getDimension(R.dimen.height_Tnormal).toInt()
            else
                eneCloud.layoutParams.height = resources.getDimension(R.dimen.height_normal).toInt()
        }
    }


    //battle preparations! UI and units -------------------------------------------
    private fun StartBattle(){
        battle.setPlayerSkills()
        enemyType = battle.createEnemies()
        battle.createEnemyCannon()
        UpdateEntitiesLocalization()
}

    private fun UpdateEntitiesDeadImage(){
        if(enemyType == 1.toShort()) {
            for(deadNo in battle.defDeadNo){//for orks arrows!
                defendersPictures[deadNo].setImageResource(R.drawable.bluedeadarrow)
            }
            for(deadNo in battle.eneDeadNo){
                enemiesPictures[deadNo].setImageResource(R.drawable.orcdead)
            }
        }else if(enemyType == 2.toShort()){
            for(deadNo in battle.defDeadNo){//for demons!
                defendersPictures[deadNo].setImageResource(R.drawable.bluedead)
            }
            for(deadNo in battle.eneDeadNo){
                enemiesPictures[deadNo].setImageResource(R.drawable.demondead)
            }
        }
        else {
            for(deadNo in battle.defDeadNo){//musket balls!
                defendersPictures[deadNo].setImageResource(R.drawable.bluedead)
            }
            for(deadNo in battle.eneDeadNo){
                enemiesPictures[deadNo].setImageResource(R.drawable.reddead)
            }
        }

        if(PlayerData.defCannon == null)
            defCannon.setImageResource(R.drawable.bluecannon_destroy)
        if(battle.enemyCannon == null)
        {
            if(enemyType == 2.toShort())
                eneCannon.setImageResource(R.drawable.scary_dead)
            else    eneCannon.setImageResource(R.drawable.enemycannon_destroy)
        }
    }

    private fun UpdateEntitiesLocalization(){
        if(PlayerData.defCannon != null) {
            defCannon.setImageResource(R.drawable.bluecannon)
            defCannon.visibility = View.VISIBLE
        }
        if(battle.sharedDataClass.enemyHasCannon) {
            if(battle.sharedDataClass.enemyType == 2.toShort())
                eneCannon.setImageResource(R.drawable.scary_demon)
            else    eneCannon.setImageResource(R.drawable.redcannon)
            eneCannon.visibility = View.VISIBLE
        }

        for(x in 0 until PlayerData.defenders.count()){
            PlayerData.defenders[x].myGamePictueNo = x
            defendersPictures[x].setImageResource(PlayerData.defenders[x].EntityImage)
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
        cannonButton.setOnClickListener{
            if(battle.defCannonPosition == 0){
                battle.defCannonPosition = 1
                defCannon.rotation = -15f
            }else if(battle.defCannonPosition == 1){
                battle.defCannonPosition = 2
                defCannon.rotation = -25f
            }else{battle.defCannonPosition = 0
            defCannon.rotation = 0f}
        }

        fireButton.setOnClickListener{
            //Toast.makeText(this@MainActivity, Random.nextInt(0, 4).toString(), Toast.LENGTH_SHORT).show()
            try {
                //battle.shootingTurnEnd = false //must reset that state !
                if(CheckBattleIsActiveStatus())
                    PerformShooting(Random.nextInt(0,2))
                else{
                    Toast.makeText(this@BattleActivity, "THE END", Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                Toast.makeText(this@BattleActivity, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        moveButton.setOnClickListener{
            if(CheckBattleIsActiveStatus()) {
                val test = ""//val test:Int = (PlayerData.defenders[0] as Defender).expPoints
                Toast.makeText(this@BattleActivity, "Battle is not finished! $test", Toast.LENGTH_SHORT).show()
            }
            else{
                //battle.battleStatus = 'X' //must reset battle state !
                //HideAllUIElements()
                //StartBattle()
                //todo get money from battle based on map difficulty
                if(battle.battleStatus == 'V') {
                    val multiplier:Short = battle.victoryMultiplier()
                    PlayerData.playerEXP = (PlayerData.playerEXP + multiplier).toShort()
                    PlayerData.playerLocations.add(PlayerData.locationToAttack)
                    PlayerData.gold = (PlayerData.gold + 300*multiplier).toShort()
                }
                PlayerData.locationToAttack = 66
                PlayerData.playerLocIsAttacked = false
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
    private fun PerformShooting(rand:Int){
        Toast.makeText(this@BattleActivity, battle.enemies.count().toString(), Toast.LENGTH_SHORT).show()
        if(rand == 0) ShootingCloud(true)
        else {
            if(battle.defAlwaysShootingFirst) ShootingCloud(true)
            else ShootingCloud(false)
        }
    }
    private fun setBelligerentCloud(isPlayer: Boolean){
        if(enemyType == 1.toShort() && !isPlayer) {
            eneCloud.setImageResource(R.drawable.arrowcloud)//orks arrows!
            //play shooting orks
            battleSound.playBowSound()
            //battleSound.playDemonSound()
        }else if(enemyType == 2.toShort() && !isPlayer)battleSound.playDemonSound()
        else {
            eneCloud.setImageResource(R.drawable.firingcloud)
            //play shooting muskets
            battleSound.playMusketSound()
        }
    }

    private fun ShootingCloud(isPlayer:Boolean){
        if(!CheckBelligerentsCount())
            return
        setBelligerentCloud(isPlayer) //todo and shooting sounds to play!

        var counter:Short= 0
        SetBattleMenuVis(false)
        if(isPlayer) {
            defCloud.visibility = View.VISIBLE
            battleText.text = battle.playerIsShooting()
            UpdateEntitiesDeadImage()
        }
        else {//enemy shooting
            eneCloud.visibility = View.VISIBLE
            battleText.text = battle.enemyIsShooting()
            if(enemyType != 1.toShort())
                UpdateEntitiesDeadImage()
        }

        timer = object: CountDownTimer(2000, 500) {
            override fun onTick(millisUntilFinished: Long) {
                counter++
                if(counter == 2.toShort()) {
                    if(isPlayer)
                        defCloud.setImageResource(R.drawable.firingcloud1)
                    else {
                        if(enemyType == 1.toShort()) {
                            defCloud.setImageResource(R.drawable.arrowcloud)
                            defCloud.visibility = View.VISIBLE
                            eneCloud.visibility = View.INVISIBLE
                        }
                        else eneCloud.setImageResource(R.drawable.firingcloud1)
                    }
                }
                else if(counter == 3.toShort()) {
                    if(isPlayer)
                        defCloud.setImageResource(R.drawable.firingcloud2)
                    else {
                        if(enemyType == 1.toShort()) {
                            UpdateEntitiesDeadImage()
                            defCloud.setImageResource(R.drawable.firingcloud)
                            defCloud.visibility = View.INVISIBLE
                        }
                        else eneCloud.setImageResource(R.drawable.firingcloud2)
                    }
                }
            }

            override fun onFinish() {
                timer.cancel()
                if(battle.battleStatus == 'X')
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
            battle.battleStatus == 'D' -> {
                battleText.text = "We have lost the battle!"
                SetBattleMenuVis(true)
                false
            }
            battle.battleStatus == 'V' -> {
                battleText.text = "The battle is victorious!"
                SetBattleMenuVis(true)
                false
            }
            else -> true
        }
    }

    private fun SetBattleMenuVis(isVisible:Boolean){
        if(isVisible){
            fireButton.visibility = View.VISIBLE
            moveButton.visibility =View.VISIBLE
            if(PlayerData.defCannon != null)
                cannonButton.visibility = View.VISIBLE
        }else{
            fireButton.visibility = View.INVISIBLE
            moveButton.visibility =View.INVISIBLE
            cannonButton.visibility = View.INVISIBLE
        }
    }

    // check if tablet test
    private fun isTablet(context: Context): Boolean {
        return context.resources.configuration.screenLayout and
                Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }
}
