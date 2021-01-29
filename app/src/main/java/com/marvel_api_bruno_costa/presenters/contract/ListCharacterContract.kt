package com.marvel_api_bruno_costa.presenters.contract

import com.marvel_api_bruno_costa.model.Result

interface ListCharacterContract {

    interface View {
        fun setupView()
        fun startLoad()
        fun stopLoad()
        fun goToDetailCharacter(characterId: String)
        fun showListCharacters(results: List<Result>)
        fun onFailure(messageError: String)
    }

    interface Presenter {
        suspend fun getListCharacters()
        fun goToDetailCharacter(characterId: String)
    }
}