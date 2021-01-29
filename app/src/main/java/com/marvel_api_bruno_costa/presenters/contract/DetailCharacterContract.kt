package com.marvel_api_bruno_costa.presenters.contract

import com.marvel_api_bruno_costa.model.Result

interface DetailCharacterContract {

    interface View {
        fun setupView()
        fun startLoad()
        fun stopLoad()
        fun onFailure(messageError: String)
        fun showDetailsCharacter(result: Result)
        fun gotoMostExpensive()
    }

    interface Presenter {
        suspend fun getDetailCharacter(characterId: String)
        fun getMostExpensive()
    }
}