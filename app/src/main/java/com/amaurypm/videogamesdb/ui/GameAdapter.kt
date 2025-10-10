package com.amaurypm.videogamesdb.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amaurypm.videogamesdb.data.db.model.GameEntity

class GameAdapter(
    private val onGameClick: (GameEntity) -> Unit
): RecyclerView.Adapter<GameViewHolder>() {

    private var games = mutableListOf<GameEntity>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameViewHolder =
        GameViewHolder.create(parent, onGameClick)

    override fun onBindViewHolder(
        holder: GameViewHolder,
        position: Int
    ) {
        holder.bind(games[position])
    }

    override fun getItemCount(): Int = games.size

    //Función para actualizar el recycler view
    fun updateList(list: List<GameEntity>){
        games.clear()
        games.addAll(list)
        notifyDataSetChanged()
    }

}