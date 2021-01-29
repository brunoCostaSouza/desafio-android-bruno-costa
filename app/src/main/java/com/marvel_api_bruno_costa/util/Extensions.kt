package com.marvel_api_bruno_costa.util

import com.marvel_api_bruno_costa.model.ComicPrice

fun List<ComicPrice>.getMostExpensive(): String {
    val auxPrice: Float = 0.0f
    var auxComicPrice: ComicPrice? = null
    this.forEach {
        if(it.price >= auxPrice) {
            auxComicPrice = it
        }
    }
    val type = when(auxComicPrice!!.type) {
        "printPrice" -> "Print price"
        "digitalPurchasePrice" -> "Digital price"
        else -> "Unknown"
    }
    return "Price: ${auxComicPrice!!.price} USD - Type: $type"
}