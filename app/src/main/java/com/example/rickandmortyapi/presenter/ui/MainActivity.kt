package com.example.rickandmortyapi.presenter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.ActivityMainBinding
import com.example.rickandmortyapi.di.MyApp
import com.example.rickandmortyapi.presenter.viewmodels.FeedViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var feedViewModelFactory: ViewModelProvider.Factory

    private val viewModel: FeedViewModel by viewModels{feedViewModelFactory}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (applicationContext as MyApp).appComponent.inject(this)
        supportFragmentManager.commit {
            val fragment = FeedFragment()
            replace(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
        }

    }

}