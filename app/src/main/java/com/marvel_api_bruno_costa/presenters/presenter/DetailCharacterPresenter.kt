package com.marvel_api_bruno_costa.presenters.presenter

import com.marvel_api_bruno_costa.presenters.contract.DetailCharacterContract
import com.marvel_api_bruno_costa.repositories.api.ApiComics
import com.marvel_api_bruno_costa.repositories.api.ResponseComics
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailCharacterPresenter(
    private val mView: DetailCharacterContract.View
) : DetailCharacterContract.Presenter, KoinComponent {

    private val mApiComics: ApiComics by inject()

    override fun getMostExpensive() {
        mView.gotoMostExpensive()
    }

    override suspend fun getDetailCharacter(characterId: String) {
        mView.startLoad()

        mApiComics.getCharacter(characterId).enqueue(object : Callback<ResponseComics> {

            override fun onFailure(call: Call<ResponseComics>, t: Throwable) {
                mView.stopLoad()
                mView.onFailure("Failed to fetch details")
            }

            override fun onResponse(
                call: Call<ResponseComics>,
                response: Response<ResponseComics>
            ) {
                mView.stopLoad()
                mView.run {
                    if (response.isSuccessful) {
                        val responseComics = response.body()
                        val result = responseComics?.data?.results
                        if (result != null && result.isNotEmpty()) {
                            showDetailsCharacter(result.first())
                        } else {
                            onFailure("Character not found")
                        }
                    } else {
                        onFailure("Failed to fetch heroes")
                    }
                }
            }
        })
    }
}