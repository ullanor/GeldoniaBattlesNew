package com.example.geldonialinebattles.Entities

//import android.view.View
//import android.widget.ImageView
import com.example.geldonialinebattles.R
import java.io.Serializable

abstract class Entity(val name:String, var health:Int,
                     val shootingSkill:Int) : Serializable{
//      var myGamePicture: ImageView? = null
    var myGamePictueNo: Int = 0

/*    fun SelectNewPicture(picture:ImageView){
        myGamePicture = picture
        myGamePicture?.setImageResource(EntityImage)
    }*/
/*    fun ShowMeOnField(){
        myGamePicture?.visibility = View.VISIBLE
    }*/
    fun AssessDamage():Boolean{
        health -= 100
        if(health <= 0){
            //myGamePicture?.setImageResource(EntityDeadImage)
            return true
        }
        return false
    }

    open val EntityImage:Int = R.drawable.redfis
    //open val EntityDeadImage:Int = R.drawable.reddead
}

//enemy test
class RedEnemyTest(name: String,health: Int,shootingSkill: Int)
    :Entity(name,health,shootingSkill){

    override val EntityImage:Int = R.drawable.redfis

}