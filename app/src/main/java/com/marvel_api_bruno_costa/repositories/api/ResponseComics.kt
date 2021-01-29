package com.marvel_api_bruno_costa.repositories.api

import com.marvel_api_bruno_costa.model.Result

class ResponseComics(
    var data: DataModel
)

class DataModel(
    var total: Int,
    var results: List<Result>
)