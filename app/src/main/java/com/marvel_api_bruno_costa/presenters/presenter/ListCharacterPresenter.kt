package com.marvel_api_bruno_costa.presenters.presenter

import com.marvel_api_bruno_costa.presenters.contract.ListCharacterContract
import com.marvel_api_bruno_costa.repositories.api.ApiComics
import com.marvel_api_bruno_costa.repositories.api.ResponseComics
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListCharacterPresenter(
    private val mView: ListCharacterContract.View
) : ListCharacterContract.Presenter, KoinComponent {

    private val mApiComics: ApiComics by inject()

    override fun goToDetailCharacter(characterId: String) {
        mView.goToDetailCharacter(characterId)
    }

    private var offSet = 0
    private var total = 1

    override suspend fun getListCharacters() {
        if(offSet < total) {
            mView.startLoad()
            mApiComics.getCharacters(offset = offSet).enqueue(object : Callback<ResponseComics> {
                override fun onFailure(call: Call<ResponseComics>, t: Throwable) {
                    mView.stopLoad()
                    mView.onFailure("Failed to fetch heroes")
                }

                override fun onResponse(
                    call: Call<ResponseComics>,
                    response: Response<ResponseComics>
                ) {
                    if (response.isSuccessful) {
                        val responseComics = response.body()
                        mView.stopLoad()
                        val data = responseComics?.data
                        val result = data?.results!!
                        total = data.total
                        offSet += result.size
                        mView.showListCharacters(result)
                    } else {
                        mView.onFailure("Failed to fetch heroes")
                    }
                }
            })
        }
    }
}