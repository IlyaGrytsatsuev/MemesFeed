package com.example.rickandmortyapi.presenter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.ActivityMainBinding
import com.example.rickandmortyapi.di.MyApp

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as MyApp).appComponent.inject(this)
        supportFragmentManager.commit {
            val fragment = FeedFragment()
            replace(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
        }

    }

}