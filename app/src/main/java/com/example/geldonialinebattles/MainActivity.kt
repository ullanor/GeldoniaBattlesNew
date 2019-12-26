package com.example.geldonialinebattles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.map_game.view.*
import android.content.Context
import android.content.DialogInterface
import android.media.MediaPlayer
import android.view.WindowManager
import com.example.geldonialinebattles.Entities.GeneralDefender
import java.io.*
import java.lang.Exception
import android.provider.Settings.System.DEFAULT_RINGTONE_URI
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.provider.Settings
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible


class MainActivity : AppCompatActivity() {
    val gameMain:GameMain = GameMain()
    var isBarracksVisible:Boolean = false
    var musicOn:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAttackLocation()
        SetButtons()
        SetUI()

        //first time on make a default save
        if(!File("$filesDir/geldonia.ser").exists()){
            ToastMe("Welcome to Geldonia!")
            serializeTest()
        }
    }

    private fun getAttackLocation(){
        mapButton.visibility = View.VISIBLE
        SaveButton.isVisible = true
        if(PlayerData.playerLocations.count() == 5){//todo VICTORY! test with 5 locations
            battleNameText.text = "FULL VICTORY!"
            MainMenu.setBackgroundResource(R.drawable.victorywall)
            mapButton.visibility = View.INVISIBLE
            return
        }
        if(PlayerData.playerLocations.count() == 0 && !PlayerData.playerLocIsAttacked){//todo GAMEOVER
            battleNameText.text = "GAME OVER!"
            mapButton.visibility = View.INVISIBLE
            SaveButton.isVisible = false
            return
        }
        if(PlayerData.playerLocIsAttacked){
            battleNameText.text = "Defend ${BattleLocation.getByValue(PlayerData.locationToAttack)}"
            mapButton.visibility = View.INVISIBLE
            SaveButton.isVisible = false
            return
        }
        battleNameText.text = "Battle of ${BattleLocation.getByValue(PlayerData.locationToAttack)}"
    }

    private fun SetUI(){
        //battleNameText.setText(PlayerData.testString)
        BarracksVisibility(false)
        UpdateUI()
        UpdateSkills()
    }

    private fun UpdateUI(){
        playerGold.text = PlayerData.gold.toString()
    }

    private fun UpdateSkills(){
        playerEXP.text = PlayerData.playerEXP.toString()

        steadFastButton.visibility = View.INVISIBLE
        quickShootButton.visibility = View.INVISIBLE
        trainedCrewButton.visibility = View.INVISIBLE

        steadFastImage.setImageResource(R.drawable.seal_grey)
        quickShootImage.setImageResource(R.drawable.seal_grey)
        trainedCrewImage.setImageResource(R.drawable.seal_grey)

        if(PlayerData.steadFast)steadFastImage.setImageResource(R.drawable.seal_green)
        if(PlayerData.quickShooter)quickShootImage.setImageResource(R.drawable.seal_red)
        if(PlayerData.trainedCrew)trainedCrewImage.setImageResource(R.drawable.seal_black)

        if(PlayerData.playerEXP >= 3){
            if(!PlayerData.steadFast)steadFastButton.visibility = View.VISIBLE
            if(!PlayerData.quickShooter)quickShootButton.visibility = View.VISIBLE
            if(!PlayerData.trainedCrew)trainedCrewButton.visibility = View.VISIBLE
        }
    }

    // buttons -------------------------------------------------------------------------------
    private fun SetButtons(){
        startBattleButton.setOnClickListener {
            if(PlayerData.defenders.count() < 3){
                Toast.makeText(this@MainActivity,
                    "You need at least 3 troops to start a battle!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(PlayerData.locationToAttack == 66.toShort()){
                Toast.makeText(this@MainActivity,
                    "Choose attack location!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            moveToStartBattle()
        }

        //map button -----------------------------------------------
        mapButton.setOnClickListener{
            //gameMain.createTestDefenders()
            moveToMap()
        }

        StopMusicButton.setOnClickListener{//music background player
            musicOn = if(musicOn){
                stopService(Intent(this,MusicService::class.java))
                false
            }else{
                startService(Intent(this,MusicService::class.java))
                true
            }
        }

        Test.setOnClickListener{//todo for defenders stats etc. and general spec skills
            //todo DISPLAY IMAGE AS ALERT DIALOG WITH DESCRIPTION OF ALL GAME FEATURES -> SEALS,SKILLS,TROOPS STATS
/*            for(def in PlayerData.defenders)if(def is GeneralDefender){
                def.AlwaysShootingFirst = true
                ToastMe(def.AlwaysShootingFirst.toString())
                break
            }*/

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.map_game,null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                //.setTitle("Map Form")
            val mAlertDialog = mBuilder.create()
            mAlertDialog.show()
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(mAlertDialog.window?.attributes)
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            mAlertDialog.window?.attributes = layoutParams

            mDialogView.location1.setOnClickListener{
                mAlertDialog.dismiss()
                Toast.makeText(this@MainActivity,"WORKING!",Toast.LENGTH_SHORT).show()
            }
            mDialogView.location2.setOnClickListener{
                mAlertDialog.dismiss()
            }
        }
        // player skills buttons ----------------------------------------------------------------
        steadFastButton.setOnClickListener{
            PlayerData.playerEXP = (PlayerData.playerEXP - 3).toShort();
            PlayerData.steadFast = true
            UpdateSkills()
        }
        quickShootButton.setOnClickListener{
            PlayerData.playerEXP = (PlayerData.playerEXP - 3).toShort();
            PlayerData.quickShooter = true
            UpdateSkills()
        }
        trainedCrewButton.setOnClickListener{
            PlayerData.playerEXP = (PlayerData.playerEXP - 3).toShort();
            PlayerData.trainedCrew = true
            UpdateSkills()
        }

        // save and load buttons -------------------------------
        SaveButton.setOnClickListener{
            serializeTest()
        }

        LoadButton.setOnClickListener{
/*            if(!File("$filesDir/geldonia.ser").exists()){
                ToastMe("No saved data found!")
                return@setOnClickListener
            }*/
            deserializeTest()
            PlayerData.playerLocIsAttacked = false
            UpdateUI()
            getAttackLocation()
            isBarracksVisible = false
            BarracksVisibility(false)
            UpdateSkills()
        }

        //shop buttons --------------------------------------------- ++++ --------------------
        shopButton.setOnClickListener{
            if(isBarracksVisible){
                isBarracksVisible = false
                BarracksVisibility(false)
                return@setOnClickListener
            }
            isBarracksVisible = true
            CountDefendersByType()
        }

        buyFusilierButton.setOnClickListener{
            if(PlayerData.gold < 50.toShort()|| PlayerData.defenders.count() >= 10){
                Toast.makeText(this@MainActivity,"Not enough gold or too many defenders!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            gameMain.buyFusilier()
            UpdateUI()
            CountDefendersByType()
        }
        //remFus
        remFusilierButton.setOnClickListener{
            gameMain.remFusilier()
            UpdateUI()
            CountDefendersByType()
        }

        buyGrenadierButton.setOnClickListener{
            if(PlayerData.gold < 300.toShort() || PlayerData.defenders.count() >= 10){
                Toast.makeText(this@MainActivity,"Not enough gold or too many defenders!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            gameMain.buyGrenadier()
            UpdateUI()
            CountDefendersByType()
        }
        //remGren
        remGrenadierButton.setOnClickListener{
            gameMain.remGrenadier()
            UpdateUI()
            CountDefendersByType()
        }

        buyGeneralButton.setOnClickListener{
            if(PlayerData.gold < 750.toShort() || gameMain.generalCount != 0.toShort() || PlayerData.defenders.count() >= 10){
                Toast.makeText(this@MainActivity,"Not enough gold or too many defenders!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            gameMain.buyGeneral()
            UpdateUI()
            CountDefendersByType()
        }
        //remGen
        remGeneralButton.setOnClickListener{
            gameMain.remGeneral()
            UpdateUI()
            CountDefendersByType()
        }

        buyCannonButton.setOnClickListener{
            if(PlayerData.gold < 1000.toShort()|| PlayerData.defCannon != null){
                Toast.makeText(this@MainActivity,"Not enough gold or cannon is already in army!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            gameMain.buyCannon()
            UpdateUI()
            CountDefendersByType()
        }
        //remCannon
        remCannonButton.setOnClickListener{
            gameMain.remCannon()
            UpdateUI()
            CountDefendersByType()
        }
    }

    private fun CountDefendersByType(){
        gameMain.countDefendersByType()
        BarracksVisibility(true)
    }

    private fun BarracksVisibility(isVisible:Boolean){
        if(isVisible) {
            fusRow.visibility = View.VISIBLE
            greRow.visibility = View.VISIBLE
            genRow.visibility = View.VISIBLE
            canRow.visibility = View.VISIBLE
            fusText.text = "X"+ gameMain.fusiliersCount + " 50g"
            greText.text = "X"+ gameMain.grenadiersCount + " 300g"
            genText.text = "X"+ gameMain.generalCount + " 750g"
            canText.text = gameMain.cannonStatus()
        }
        else{
            fusRow.visibility = View.INVISIBLE
            greRow.visibility = View.INVISIBLE
            genRow.visibility = View.INVISIBLE
            canRow.visibility = View.INVISIBLE
        }
    }

    //start battle test (change activity to battle and pass battle id/name

    private fun moveToStartBattle(){
        val intent = Intent(this,BattleActivity::class.java)
        //intent.putExtra("battleID",4)
        startActivity(intent)
    }

    //show map (change activity to map)

    private fun moveToMap(){
        val intent = Intent(this,MapActivity::class.java)
        startActivity(intent)
    }

    // alert dialog test message --------------------------------------------------------
    private fun alertDialogInfo(message:String){
        lateinit var dialog:AlertDialog
        val mBuilder = AlertDialog.Builder(this)
              .setTitle("Load Info")
              .setMessage(message)

        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
/*                DialogInterface.BUTTON_POSITIVE ->
                    Toast.makeText(this@MainActivity,"Positive/Yes button clicked.",
                        Toast.LENGTH_SHORT).show()*/
            }
        }
        // Set the alert dialog positive/yes button
        mBuilder.setPositiveButton("YES",dialogClickListener)
        dialog = mBuilder.create()
        dialog.show()
    }

    //serializations ~ SAVING AND LOADING GAME~ -----------------------------------------
    fun serializeTest() {
        val file = "geldonia.ser"
        val toSaveData = GeldoniaSaveData(PlayerData.gold,PlayerData.defenders,
            PlayerData.locationToAttack,PlayerData.defCannon,PlayerData.playerLocations,
            PlayerData.playerEXP,PlayerData.trainedCrew,PlayerData.steadFast,PlayerData.quickShooter)

        var fos: FileOutputStream? = null
        fos = openFileOutput(file, Context.MODE_PRIVATE)
        try {
            ObjectOutputStream(fos).use { it.writeObject(toSaveData) }
            Toast.makeText(
                this@MainActivity,
                "Saving file to $filesDir/$file",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Toast.makeText(this@MainActivity, e.toString(), Toast.LENGTH_SHORT).show()
        }
        fos?.close()
    }

    fun deserializeTest(){
        val file = "geldonia.ser"
        var fis: FileInputStream

        fis = openFileInput(file)

        ObjectInputStream(fis).use { it ->
            val toLoadData = it.readObject()

            when (toLoadData){
                is GeldoniaSaveData -> {
                    //Toast.makeText(this@MainActivity, toLoadData.toString(), Toast.LENGTH_SHORT).show()
                    alertDialogInfo(toLoadData.toString())

                    PlayerData.gold = toLoadData.gold
                    PlayerData.defenders = toLoadData.defenders
                    PlayerData.locationToAttack = toLoadData.locationToAttack
                    PlayerData.defCannon = toLoadData.defCannon
                    PlayerData.playerLocations = toLoadData.playerLocations
                    PlayerData.playerEXP = toLoadData.playerExp
                    PlayerData.trainedCrew = toLoadData.trainedCrew
                    PlayerData.steadFast = toLoadData.steadFast
                    PlayerData.quickShooter = toLoadData.quickShooter
                }
                else -> Toast.makeText(this@MainActivity, "Failed to restore", Toast.LENGTH_SHORT).show()
            }
        }
        fis?.close()
    }

    //toast func---------------------------------------------
    private fun ToastMe(value:String){
        Toast.makeText(this@MainActivity,value,Toast.LENGTH_SHORT).show()
    }
}

