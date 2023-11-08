package com.example.memesfeed.presenter.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.example.memesfeed.R
import com.example.memesfeed.databinding.ActivityMainBinding
import com.example.memesfeed.di.MyApp
import com.example.memesfeed.domain.usecases.GetMemesListFromApiUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as MyApp).appComponent.inject(this)
        supportFragmentManager.commit {
            val fragment = FeedFragment()
            add(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
        }

    }

}