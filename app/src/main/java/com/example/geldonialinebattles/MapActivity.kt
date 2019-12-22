package com.example.geldonialinebattles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_map.*
import kotlin.random.Random

class MapActivity : AppCompatActivity() {
    var GameLocations:Array<ImageView> = arrayOf()
    val playerLocations:List<Short> = PlayerData.playerLocations

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        GameLocations = arrayOf(location0,location1,location2,location3,location4,location5,
            location6,location7,location8,location9,location10,location11)
        setLocationsOwnership()
        setLocations()
    }

    private fun setLocationsOwnership(){
        for(loc in PlayerData.playerLocations)GameLocations[loc.toInt()].setImageResource(R.drawable.flag_blue)
    }

    private fun CannotGetThereMsg(){
        Toast.makeText(this@MapActivity,"We cannot get there!",Toast.LENGTH_SHORT).show()
    }

    private fun CheckAmbushProbability(location:Short){
        val rand = Random.nextInt(0,3)
        if(rand == 0 && PlayerData.playerLocations.count() >= 0){
            val toRem:Short = Random.nextInt(PlayerData.playerLocations.count()).toShort()
            PlayerData.locationToAttack = PlayerData.playerLocations[toRem.toInt()]
            PlayerData.playerLocations.removeAt(toRem.toInt())
            PlayerData.playerLocIsAttacked = true
        }else{
            PlayerData.playerLocIsAttacked = false
            PlayerData.locationToAttack = location
        }
    }

    private fun setLocations(){
        location0.setOnClickListener{//blue city
            if(playerLocations.contains(0))return@setOnClickListener
            for(loc in playerLocations)if(loc0ways.contains(loc)){
                CheckAmbushProbability(0)
                quitMap()
                return@setOnClickListener
            }
            CannotGetThereMsg()
            return@setOnClickListener
        }

        location1.setOnClickListener{
            if(playerLocations.contains(1))return@setOnClickListener
            for(loc in playerLocations)if(loc1ways.contains(loc)){
                CheckAmbushProbability(1)
                quitMap()
                return@setOnClickListener
            }
            CannotGetThereMsg()
            return@setOnClickListener
        }

        location2.setOnClickListener{
            if(playerLocations.contains(2))return@setOnClickListener
            for(loc in playerLocations)if(loc2ways.contains(loc)){
                CheckAmbushProbability(2)
                quitMap()
                return@setOnClickListener
            }
            CannotGetThereMsg()
            return@setOnClickListener
        }

        location3.setOnClickListener{
            if(playerLocations.contains(3))return@setOnClickListener
            for(loc in playerLocations)if(loc3ways.contains(loc)){
                CheckAmbushProbability(3)
                quitMap()
                return@setOnClickListener
            }
            CannotGetThereMsg()
            return@setOnClickListener
        }

        location4.setOnClickListener{
            if(playerLocations.contains(4))return@setOnClickListener
            for(loc in playerLocations)if(loc4ways.contains(loc)){
                CheckAmbushProbability(4)
                quitMap()
                return@setOnClickListener
            }
            CannotGetThereMsg()
            return@setOnClickListener
        }

/*        location5.setOnClickListener{
            if(playerLocations.contains(5))return@setOnClickListener
            for(loc in playerLocations)if(loc5ways.contains(loc)){
                CheckAmbushProbability(5)
                quitMap()
                return@setOnClickListener
            }
            CannotGetThereMsg()
            return@setOnClickListener
        }*/
    }

    //return to main menu
    private fun quitMap(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    val loc0ways:ShortArray = shortArrayOf(1)
    val loc1ways:ShortArray = shortArrayOf(0,2,4)
    val loc2ways:ShortArray = shortArrayOf(1,3)
    val loc3ways:ShortArray = shortArrayOf(2)
    val loc4ways:ShortArray = shortArrayOf(1,5)
    val loc5ways:ShortArray = shortArrayOf(4,6)
    val loc6ways:ShortArray = shortArrayOf(5,7)
    val loc7ways:ShortArray = shortArrayOf(6,8,10)
    val loc8ways:ShortArray = shortArrayOf(7,9,10)
    val loc9ways:ShortArray = shortArrayOf(8,10)
    val loc10ways:ShortArray = shortArrayOf(7,8,9,11)
    val loc11ways:ShortArray = shortArrayOf(7,10)

}
