package com.example.memesfeed.presenter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.memesfeed.R
import com.example.memesfeed.di.MyApp
import com.example.memesfeed.domain.usecases.GetMemesListFromApiUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var getMemesListFromApiUseCase: GetMemesListFromApiUseCase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MyApp).appComponent.inject(this)
            lifecycleScope.launch {
                try {
                    getMemesListFromApiUseCase.execute()
                }
                catch (e:Exception){
                    Log.d("exception", e.message?:"")
                }
            }
    }

}