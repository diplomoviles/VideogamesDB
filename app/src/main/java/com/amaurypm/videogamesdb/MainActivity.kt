package com.amaurypm.videogamesdb

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.amaurypm.videogamesdb.application.VideogamesDBApp
import com.amaurypm.videogamesdb.data.GameRepository
import com.amaurypm.videogamesdb.data.db.model.GameEntity
import com.amaurypm.videogamesdb.databinding.ActivityMainBinding
import com.amaurypm.videogamesdb.ui.GameAdapter
import com.amaurypm.videogamesdb.ui.GameDialog
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var games = mutableListOf<GameEntity>()
    private lateinit var repository: GameRepository

    private lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        repository = (application as VideogamesDBApp).repository

        gameAdapter = GameAdapter{ selectedGame ->
            //Click a cada juego dado de alta
            Toast.makeText(
                this,
                "Click en el juego: ${selectedGame.title}",
                Toast.LENGTH_SHORT
            ).show()
        }

        /*binding.rvGames.layoutManager = LinearLayoutManager(this)
        binding.rvGames.adapter = gameAdapter*/

        binding.rvGames.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = gameAdapter
        }

        updateUI()


    }

    private fun updateUI(){
        lifecycleScope.launch {
            games = repository.getAllGames()

            binding.tvSinRegistros.visibility =
                if(games.isNotEmpty()) View.INVISIBLE else View.VISIBLE

            gameAdapter.updateList(games)
        }
    }

    fun click(view: View) {
        //Click del Floating action button
        /*val i = Random.nextInt(0, 100)

        val game = GameEntity(
            title = "Juego $i",
            genre = "Género $i",
            developer = "Desarrollador $i"
        )

        lifecycleScope.launch {
            repository.insertGame(game)
        }*/

        val dialog = GameDialog()
        dialog.show(supportFragmentManager, "dialog1")

    }
}