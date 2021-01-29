package com.marvel_api_bruno_costa.presenters.presenter

import com.marvel_api_bruno_costa.presenters.contract.MostExpensiveContract
import com.marvel_api_bruno_costa.repositories.api.ApiComics
import com.marvel_api_bruno_costa.repositories.api.ResponseComics
import com.marvel_api_bruno_costa.util.Utils
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MostExpensivePresenter(
    private val mView: MostExpensiveContract.View
) : MostExpensiveContract.Presenter, KoinComponent {

    private val mApiComics: ApiComics by inject()

    override suspend fun getMostExpensive(characterId: String?) {
        mView.startLoad()

        if(characterId == null) {
            mView.stopLoad()
            mView.onFailure("Comic most expensive not found")
            return
        }

        mApiComics.getComics(characterId).enqueue(object : Callback<ResponseComics> {

            override fun onFailure(call: Call<ResponseComics>, t: Throwable) {
                mView.stopLoad()
                mView.onFailure("Failed to fetch comic")
            }

            override fun onResponse(call: Call<ResponseComics>, response: Response<ResponseComics>) {
                mView.stopLoad()
                mView.run {
                    if (response.isSuccessful) {
                        val responseComics = response.body()
                        val result = Utils.getMostExpensiveHq(responseComics?.data?.results)
                        mView.showMostExpensiveQh(result)
                    } else {
                        onFailure("Comic most expensive not found")
                    }
                }
            }
        })
    }
}