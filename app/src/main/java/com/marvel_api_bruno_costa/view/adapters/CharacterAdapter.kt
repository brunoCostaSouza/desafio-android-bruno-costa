package com.marvel_api_bruno_costa.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_android_bruno_costa.R
import com.marvel_api_bruno_costa.model.Result
import com.marvel_api_bruno_costa.view.interfaces.InteractionItem
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_character.view.*
import java.lang.Exception

class CharacterAdapter(
    private val context: Context,
    private val interaction: InteractionItem
) : RecyclerView.Adapter<CharacterViewHolder>() {

    private var listCharacters = mutableListOf<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {

        return CharacterViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_list_character, parent, false),
            interaction
        )
    }

    override fun getItemCount() = listCharacters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(listCharacters[position])
    }

    fun updateList(results: List<Result>) {
        listCharacters.addAll(results)
    }
}

class CharacterViewHolder(
    itemView: View,
    private val interaction: InteractionItem
) : RecyclerView.ViewHolder(itemView) {
    fun bind(result: Result) {
        itemView.txt_name.text = result.name
        itemView.txt_description.text = result.description.takeIf { description -> description.isNotBlank() }?: "No Description"
        Picasso.get().load(result.thumbnail.absolutePath).into(itemView.ic_character, object: Callback {
            override fun onSuccess() {}
            override fun onError(e: Exception?) { itemView.ic_character.setImageResource(R.drawable.marvel) }
        })
        itemView.cl_main.setOnClickListener {
            interaction.onClickItem(result)
        }
    }
}