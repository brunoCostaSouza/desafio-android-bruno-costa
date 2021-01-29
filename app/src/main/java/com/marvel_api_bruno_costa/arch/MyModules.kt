package com.marvel_api_bruno_costa.arch

import com.marvel_api_bruno_costa.presenters.contract.DetailCharacterContract
import com.marvel_api_bruno_costa.presenters.presenter.DetailCharacterPresenter
import com.marvel_api_bruno_costa.presenters.contract.ListCharacterContract
import com.marvel_api_bruno_costa.presenters.contract.MostExpensiveContract
import com.marvel_api_bruno_costa.presenters.presenter.ListCharacterPresenter
import com.marvel_api_bruno_costa.presenters.presenter.MostExpensivePresenter
import com.marvel_api_bruno_costa.repositories.api.ApiComics
import org.koin.dsl.module

val netWorkModule = module {
    single { ApiComics.create() }
}

val presenterModule = module {
    factory { (view: ListCharacterContract.View) -> ListCharacterPresenter(view) }
    factory { (view: DetailCharacterContract.View) -> DetailCharacterPresenter(view) }
    factory { (view: MostExpensiveContract.View) -> MostExpensivePresenter(view) }
}