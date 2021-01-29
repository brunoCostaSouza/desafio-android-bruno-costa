package com.marvel_api_bruno_costa.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafio_android_bruno_costa.R
import com.marvel_api_bruno_costa.model.Result
import com.marvel_api_bruno_costa.presenters.contract.DetailCharacterContract
import com.marvel_api_bruno_costa.presenters.presenter.DetailCharacterPresenter
import com.marvel_api_bruno_costa.util.Utils
import com.marvel_api_bruno_costa.view.adapters.ComicsAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_character_detail.*
import kotlinx.coroutines.*
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.get
import java.lang.Exception

class DetailCharacterActivity : BaseComicActivity(), DetailCharacterContract.View {

    private val mPresenter: DetailCharacterContract.Presenter = get(
        DetailCharacterPresenter::class.java, parameters = { parametersOf(this)}
    )

    private lateinit var mAdapter: ComicsAdapter
    private var characterId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)
        setTitle(R.string.title_detail)

        intent.extras?.getString(Utils.BundleKey.CHARACTER_ID)?.let {
            characterId = it
        }

        setupView()
    }

    override fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fab.setOnClickListener { mPresenter.getMostExpensive()  }
        mAdapter = ComicsAdapter(this)
        rc_comics.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        characterId?.let {
            scope.launch {
                mPresenter.getDetailCharacter(it)
            }
        }
    }

    override fun showDetailsCharacter(result: Result) {
        showLayoutContent()
        result.let {
            mAdapter.updateList(it.comics.items)
            txt_name.text = it.name

            txt_description.text = it.description.takeIf { description -> description.isNotBlank() } ?: "No description"
            Picasso.get().load(result.thumbnail.absolutePath).into(img_character, object: Callback {
                override fun onSuccess() {}
                override fun onError(e: Exception?) { img_character.setImageResource(R.drawable.marvel) }
            })
        }
    }

    override fun gotoMostExpensive() {
        val intent = Intent(this, MostExpensiveActivity::class.java)
        intent.putExtra(Utils.BundleKey.CHARACTER_ID, characterId)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun startLoad() {
        runOnUiThread { progress.visibility = View.VISIBLE }
    }

    override fun stopLoad() {
        runOnUiThread { progress.visibility = View.GONE }
    }

    override fun onFailure(messageError: String) {
        showLayoutError()
    }

    private fun showLayoutContent() {
        runOnUiThread {
            cl_content.visibility = View.VISIBLE
            cl_error.visibility = View.GONE
        }
    }

    private fun showLayoutError() {
        runOnUiThread {
            cl_content.visibility = View.GONE
            cl_error.visibility = View.VISIBLE
        }
    }
}