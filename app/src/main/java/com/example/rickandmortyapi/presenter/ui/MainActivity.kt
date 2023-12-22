package com.example.rickandmortyapi.presenter.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.ActivityMainBinding
import com.example.rickandmortyapi.di.daggerComponents.DaggerMainActivityComponent
import com.example.rickandmortyapi.di.daggerComponents.MainActivityComponent
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.FragmentNavigator
import com.example.rickandmortyapi.presenter.viewmodels.InternetConnectionObserverViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentNavigator {

    private lateinit var binding: ActivityMainBinding


    private val viewModel: InternetConnectionObserverViewModel by viewModels{viewModelFactory}

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val activityComponent: MainActivityComponent by lazy {
        DaggerMainActivityComponent.factory().create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityComponent.inject(this)
        setUpInternetConnectionObserver()
        supportFragmentManager.commit {
            val fragment = FeedFragment()
            add(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
        }

    }

    private fun setUpInternetConnectionObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.connectionState.collect{
                        if(!it)
                            showSnackBar(
                                getString(R.string.no_internet_message))

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun showSnackBar(message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
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