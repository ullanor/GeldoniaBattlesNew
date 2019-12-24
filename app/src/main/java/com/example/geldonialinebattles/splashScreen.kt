package com.example.geldonialinebattles

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class splashScreen: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen_activity)

        Handler().postDelayed(
            {
                startActivity(Intent(this,MainActivity::class.java))
            },
            500L
        )
    }
}