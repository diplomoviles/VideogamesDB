package com.amaurypm.videogamesdb.application

import android.app.Application
import com.amaurypm.videogamesdb.data.GameRepository
import com.amaurypm.videogamesdb.data.db.GameDatabase

class VideogamesDBApp: Application() {

    private val database by lazy{
        GameDatabase.getDatabase(this)
    }

    val repository by lazy{
        GameRepository(database.gameDao())
    }

}