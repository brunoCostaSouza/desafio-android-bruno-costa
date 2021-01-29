package com.marvel_api_bruno_costa.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_android_bruno_costa.R
import com.marvel_api_bruno_costa.model.Comic
import kotlinx.android.synthetic.main.item_list_character.view.txt_description

class ComicsAdapter(
    private val context: Context
) : RecyclerView.Adapter<ComicsViewHolder>() {

    private var listComicsItem = mutableListOf<Comic.Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        return ComicsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_list_comics, parent, false)
        )
    }

    override fun getItemCount() = listComicsItem.size

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        holder.bind(listComicsItem[position])
    }

    fun updateList(characters: List<Comic.Item>) {
        listComicsItem.clear()
        listComicsItem.addAll(characters)
        notifyDataSetChanged()
    }
}

class ComicsViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Comic.Item) {
        itemView.txt_description.text = item.name
    }
}