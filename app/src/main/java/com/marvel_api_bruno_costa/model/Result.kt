package com.marvel_api_bruno_costa.model

class Result(
    var id: Long,
    var name: String,
    var description: String,
    var thumbnail: Thumbnail,
    var comics: Comic,

    var title: String,
    var prices: List<ComicPrice>,
    var images: List<Thumbnail>
)