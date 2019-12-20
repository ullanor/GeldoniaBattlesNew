package com.example.geldonialinebattles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        setLocations()
    }

    private fun setLocations(){
        location1.setOnClickListener{
            PlayerData.locationToAttack = 1
            quitMap()
        }

        location2.setOnClickListener{
            PlayerData.locationToAttack = 2
            quitMap()
        }

        location3.setOnClickListener{
            PlayerData.locationToAttack = 3
            quitMap()
        }

        location4.setOnClickListener{
            PlayerData.locationToAttack = 4
            quitMap()
        }
    }

    //return to main menu
    private fun quitMap(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}
