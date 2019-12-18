package com.example.geldonialinebattles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import android.widget.Toast



class MainActivity : AppCompatActivity() {
    val gameMain:GameMain = GameMain()
    var isBarracksVisible:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SetButtons()
        SetUI()
        Toast.makeText(this@MainActivity,PlayerData.defenders.count().toString(),Toast.LENGTH_SHORT).show()
    }

    private fun SetUI(){
        editText.setText(PlayerData.testString)

        BarracksVisibility(false)
        UpdateUI()
    }

    private fun UpdateUI(){
        mapButton.text = PlayerData.gold.toString()
    }

    private fun SetButtons(){
        startBattleButton.setOnClickListener {
            if(PlayerData.defenders.count() < 3){
                Toast.makeText(this@MainActivity,
                    "You need at least 3 troops to start a battle!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            moveToStartBattle()
        }

        Test.setOnClickListener{
            gameMain.createTestDefenders()
            PlayerData.testString = "changed!"
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
        intent.putExtra("battleID",4)
        startActivity(intent)
    }
}

