package com.marvel_api_bruno_costa.model

class Comic(
    var items: List<Item>
) {
    inner class Item {
        var name: String? = null
    }
}