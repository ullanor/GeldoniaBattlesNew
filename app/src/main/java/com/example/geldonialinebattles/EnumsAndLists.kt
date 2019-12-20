package com.example.geldonialinebattles

data class SharedDataClass(var battleDifficulty:Char)

enum class BattleLocation(val location:Short){
    none(0),
    blueCity(1),
    plain(2),
    lake(3),
    redCity(4);

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
    redCity(R.drawable.cityred);
}