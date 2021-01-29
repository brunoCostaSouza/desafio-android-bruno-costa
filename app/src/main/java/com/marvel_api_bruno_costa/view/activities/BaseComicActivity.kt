package com.marvel_api_bruno_costa.view.activities

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseComicActivity : AppCompatActivity() {
    protected val scope = CoroutineScope(Dispatchers.Default + Job())
}