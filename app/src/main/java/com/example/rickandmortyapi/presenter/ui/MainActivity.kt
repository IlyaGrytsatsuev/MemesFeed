package com.example.rickandmortyapi.presenter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.ActivityMainBinding
import com.example.rickandmortyapi.di.MyApp
import com.example.rickandmortyapi.di.daggerComponents.MainActitvityComponent
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.FragmentNavigator
import com.example.rickandmortyapi.presenter.viewmodels.FeedViewModel
import com.example.rickandmortyapi.presenter.viewmodels.InternetConnectionObserverViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentNavigator {

    private lateinit var binding: ActivityMainBinding


//    private val viewModel: InternetConnectionObserverViewModel by viewModels{feedViewModelFactory}
//
//
//    private val activityComponent: MainActitvityComponent by lazy {
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //(applicationContext as MyApp).appComponent.inject(this)
        supportFragmentManager.commit {
            val fragment = FeedFragment()
            add(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun moveToFragment(container: Int, fragment: Fragment) {
        supportFragmentManager.commit {
            //replace(container, fragment)
            add(container, fragment)
            addToBackStack(fragment::javaClass.name)
            setReorderingAllowed(true)
        }
    }

    override fun removeUpperFragment() {
        supportFragmentManager.popBackStack()
    }


}