package com.marvel_api_bruno_costa.view.activities

import android.os.Bundle
import android.view.View
import com.example.desafio_android_bruno_costa.R
import com.marvel_api_bruno_costa.model.Result
import com.marvel_api_bruno_costa.presenters.contract.MostExpensiveContract
import com.marvel_api_bruno_costa.presenters.presenter.MostExpensivePresenter
import com.marvel_api_bruno_costa.util.Utils
import com.marvel_api_bruno_costa.util.getMostExpensive
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_most_expensive.*
import kotlinx.android.synthetic.main.activity_most_expensive.img_character
import kotlinx.android.synthetic.main.activity_most_expensive.txt_description
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent
import java.lang.Exception

class MostExpensiveActivity : BaseComicActivity(), MostExpensiveContract.View {

    private val mPresenter: MostExpensiveContract.Presenter = KoinJavaComponent.get(
        MostExpensivePresenter::class.java, parameters = { parametersOf(this) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_most_expensive)
        setupView()
    }

    override fun setupView() {
        setTitle(R.string.title_most_expensive)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        val characterId = intent.extras?.getString(Utils.BundleKey.CHARACTER_ID)
        getMostExpensive(characterId)
    }

    override fun showMostExpensiveQh(result: Result?) {
        if(result == null) showErrorLayout()

        result?.let {
            showContentLayout()
            txt_title.text = it.title
            txt_description.text = it.description
            txt_price.text = it.prices.getMostExpensive()
            if (result.images.isNotEmpty()) {
                Picasso.get().load(result.images.first().absolutePath).into(img_character, object: Callback {
                    override fun onSuccess() {}
                    override fun onError(e: Exception?) { img_character.setImageResource(R.drawable.marvel) }
                })
            }
        }

    }

    private fun getMostExpensive(characterId: String?) =
        scope.launch { mPresenter.getMostExpensive(characterId) }

    override fun startLoad() {
        progress_me.visibility = View.VISIBLE
        cl_content_me.visibility = View.GONE
        cl_error_me.visibility = View.GONE
    }

    override fun stopLoad() {
        progress_me.visibility = View.GONE
    }

    private fun showContentLayout() {
        cl_content_me.visibility = View.VISIBLE
        cl_error_me.visibility = View.GONE
    }

    private fun showErrorLayout() {
        cl_content_me.visibility = View.GONE
        cl_error_me.visibility = View.VISIBLE
    }
    override fun onFailure(messageError: String) {
        showErrorLayout()
        txt_error_me.text = messageError
    }
}