package com.marvel_api_bruno_costa.presenters.contract

import com.marvel_api_bruno_costa.model.Result

interface MostExpensiveContract {

    interface View {
        fun setupView()
        fun startLoad()
        fun stopLoad()
        fun onFailure(messageError: String)
        fun showMostExpensiveQh(result: Result?)
    }

    interface Presenter {
        suspend fun getMostExpensive(characterId: String?)
    }
}