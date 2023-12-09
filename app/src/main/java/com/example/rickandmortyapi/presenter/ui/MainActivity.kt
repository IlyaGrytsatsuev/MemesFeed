package com.example.rickandmortyapi.presenter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.ActivityMainBinding
import com.example.rickandmortyapi.di.MyApp
import com.example.rickandmortyapi.presenter.FragmentNavigator
import com.example.rickandmortyapi.presenter.viewmodels.FeedViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentNavigator {

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
            add(R.id.fragment_container, fragment)
            addToBackStack(FeedFragment::javaClass.name)
            setReorderingAllowed(true)
        }
    }

    override fun moveToFragment(container: Int, fragment: Fragment) {
        supportFragmentManager.commit {
            add(container, fragment)
            addToBackStack(fragment::javaClass.name)
            setReorderingAllowed(true)
        }
    }

    override fun removeUpperFragment() {
        supportFragmentManager.popBackStack()
    }


}