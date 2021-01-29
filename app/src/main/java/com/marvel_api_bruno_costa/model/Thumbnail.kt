package com.marvel_api_bruno_costa.model

class Thumbnail(
    var path: String,
    var extension: String

) {
    val absolutePath: String
        get() = "${path}.${extension}"
}