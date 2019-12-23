package com.example.geldonialinebattles

data class SharedDataClass(var battleDifficulty:Char,var enemyHasCannon:Boolean,
                           var enemyFightToTheEnd:Boolean,var enemyType:Short)

enum class BattleLocation(val location:Short){
    none(66),
    blueCity(0),
    plain(1),
    lake(2),
    redCity(3),
    desert(4);

    companion object {
        private val values = values()
        fun getByValue(location: Short) = values.firstOrNull { it.location == location }
    }
}

enum class BattleLocationMap(val mapLoc:Int){
    none(R.drawable.cityblue),
    blueCity(R.drawable.cityblue),
    plain(R.drawable.mapplain),
    lake(R.drawable.maplake),
    redCity(R.drawable.cityred),
    desert(R.drawable.mapdesert);
}