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
import android.view.WindowManager
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    val gameMain:GameMain = GameMain()
    var isBarracksVisible:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAttackLocation()
        SetButtons()
        SetUI()
        Toast.makeText(this@MainActivity,PlayerData.defenders.count().toString(),Toast.LENGTH_SHORT).show()
    }

    private fun getAttackLocation(){
        battleNameText.text = "Battle of ${BattleLocation.getByValue(PlayerData.locationToAttack)}"
    }

    private fun SetUI(){
        //battleNameText.setText(PlayerData.testString)

        BarracksVisibility(false)
        UpdateUI()
    }

    private fun UpdateUI(){
        playerGold.text = PlayerData.gold.toString()
    }

    // buttons -------------------------------------------------------------------------------
    private fun SetButtons(){
        startBattleButton.setOnClickListener {
            if(PlayerData.defenders.count() < 3){
                Toast.makeText(this@MainActivity,
                    "You need at least 3 troops to start a battle!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            moveToStartBattle()
        }

        //map button -----------------------------------------------
        mapButton.setOnClickListener{
            //gameMain.createTestDefenders()
            moveToMap()
        }

        Test.setOnClickListener{//todo for defenders stats etc.
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

        SaveButton.setOnClickListener{
            serializeTest()
        }

        LoadButton.setOnClickListener{
            deserializeTest()
            UpdateUI()
            getAttackLocation()
        }

        //shop buttons ---------------------------------------------
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

        buyGrenadierButton.setOnClickListener{
            if(PlayerData.gold < 300.toShort() || PlayerData.defenders.count() >= 10){
                Toast.makeText(this@MainActivity,"Not enough gold or too many defenders!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            gameMain.buyGrenadier()
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
            //genRow.visibility = View.VISIBLE
            //canRow.visibility = View.VISIBLE
            fusText.text = "X"+ gameMain.fusiliersCount + " 50g"
            greText.text = "X"+ gameMain.grenadiersCount + " 300g"
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
                DialogInterface.BUTTON_POSITIVE ->
                    Toast.makeText(this@MainActivity,"Positive/Yes button clicked.",
                        Toast.LENGTH_SHORT).show()
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
            PlayerData.locationToAttack)

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
            Toast.makeText(this@MainActivity, e.toString(), Toast.LENGTH_LONG).show()
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
                }
                else -> Toast.makeText(this@MainActivity, "Failed to restore", Toast.LENGTH_SHORT).show()
            }
        }
        fis?.close()
    }

}

