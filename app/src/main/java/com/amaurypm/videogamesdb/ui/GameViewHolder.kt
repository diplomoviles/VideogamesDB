package com.amaurypm.videogamesdb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amaurypm.videogamesdb.data.db.model.GameEntity
import com.amaurypm.videogamesdb.databinding.GameElementBinding

class GameViewHolder(
    private val binding: GameElementBinding,
    private val onGameClick: (GameEntity) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    private var currentItem: GameEntity? = null

    init{
        //Clicks a los elementos del viewholder
        binding.root.setOnClickListener {
           currentItem?.let(onGameClick)
        }

        //Click en el título
        /*binding.tvTitle.setOnClickListener {
            currentItem?.let { onGameTitleClick }
        }*/
    }

    fun bind(game: GameEntity){
        currentItem = game
        //Aquí vinculamos las vistas

        binding.apply {
            tvTitle.text = game.title
            tvGenre.text = game.genre
            tvDeveloper.text = game.developer
        }

    }

    companion object{
        fun create(
            parent: ViewGroup,
            onGameClick: (GameEntity) -> Unit
        ): GameViewHolder{
            //Inflamos cada ViewHolder
            val binding = GameElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return GameViewHolder(binding, onGameClick)
        }
    }

}