package com.marvel_api_bruno_costa.view.activities

import android.content.Intent
import android.nfc.tech.MifareUltralight
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_android_bruno_costa.R
import com.marvel_api_bruno_costa.model.Result
import com.marvel_api_bruno_costa.presenters.contract.ListCharacterContract
import com.marvel_api_bruno_costa.presenters.presenter.ListCharacterPresenter
import com.marvel_api_bruno_costa.util.Utils
import com.marvel_api_bruno_costa.view.adapters.CharacterAdapter
import com.marvel_api_bruno_costa.view.interfaces.InteractionItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.get

class ListCharacterActivity : BaseComicActivity(), ListCharacterContract.View,
    InteractionItem {

    private lateinit var mAdapter: CharacterAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    private val mPresenter: ListCharacterContract.Presenter = get(
        ListCharacterPresenter::class.java, parameters = { parametersOf(this) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        getListCharacters()
    }

    override fun setupView() {
        this.mAdapter =
            CharacterAdapter(
                this,
                this
            )
        list_character.apply {
            mLayoutManager = LinearLayoutManager(context)
            layoutManager = mLayoutManager
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.animation_item)
            adapter = mAdapter
            addOnScrollListener(recyclerViewOnScrollListener)
        }
        btn_try_again.setOnClickListener { getListCharacters() }
    }

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount = mLayoutManager.childCount
            val totalItemCount = mLayoutManager.itemCount
            val firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition()

            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= MifareUltralight.PAGE_SIZE
            ) {
                getListCharacters()
            }
        }
    }

    private fun getListCharacters() = scope.launch { mPresenter.getListCharacters() }

    override fun goToDetailCharacter(characterId: String) {
        val intent = Intent(this, DetailCharacterActivity::class.java)
        intent.putExtra(Utils.BundleKey.CHARACTER_ID, characterId)
        startActivity(intent)
    }

    override fun onClickItem(result: Result) {
        mPresenter.goToDetailCharacter(result.id.toString())
    }

    override fun startLoad() {
        runOnUiThread {
            progress.visibility = View.VISIBLE
            txt_empty.visibility = View.GONE
            btn_try_again.visibility = View.GONE
        }
    }

    override fun stopLoad() {
        runOnUiThread {
            progress.visibility = View.GONE
            txt_empty.visibility = View.GONE
        }
    }

    override fun showListCharacters(results: List<Result>) {
        txt_empty.visibility = if (results.isEmpty()) View.VISIBLE else View.GONE
        mAdapter.updateList(results)
        mAdapter.notifyDataSetChanged()
    }

    override fun onFailure(messageError: String) {
        txt_empty.visibility = View.VISIBLE
        btn_try_again.visibility = View.VISIBLE
        Toast.makeText(this, messageError, Toast.LENGTH_LONG).show()
    }
}