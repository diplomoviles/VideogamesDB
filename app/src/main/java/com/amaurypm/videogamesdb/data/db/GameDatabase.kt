package com.amaurypm.videogamesdb.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amaurypm.videogamesdb.data.db.model.GameEntity

@Database(
    entities = [GameEntity::class],
    version = 1
)
abstract class GameDatabase: RoomDatabase() {
    //Aquí va la propiedad del DAO


}