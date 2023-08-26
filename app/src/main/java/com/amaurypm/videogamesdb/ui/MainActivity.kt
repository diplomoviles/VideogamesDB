package com.amaurypm.videogamesdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.amaurypm.videogamesdb.R
import com.amaurypm.videogamesdb.application.VideogamesDBApp
import com.amaurypm.videogamesdb.data.GameRepository
import com.amaurypm.videogamesdb.data.db.model.GameEntity
import com.amaurypm.videogamesdb.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var games: List<GameEntity> = emptyList()
    private lateinit var repository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = (application as VideogamesDBApp).repository

        updateUI()

    }

    private fun updateUI(){
        lifecycleScope.launch {
            games = repository.getAllGames()

            if(games.isNotEmpty()){
                //Hay por lo menos un registro
                binding.tvSinRegistros.visibility = View.INVISIBLE
            }else{
                //No hay registros
                binding.tvSinRegistros.visibility = View.VISIBLE
            }

        }
    }
}