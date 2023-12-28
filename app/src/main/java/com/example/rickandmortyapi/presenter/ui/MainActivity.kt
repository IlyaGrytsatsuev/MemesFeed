package com.example.rickandmortyapi.presenter.ui

import android.os.Bundle
import android.view.View
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
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentNavigator {

    private lateinit var binding: ActivityMainBinding


    private val viewModel: InternetConnectionObserverViewModel by viewModels{viewModelFactory}

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val activityComponent: MainActivityComponent by lazy {
        DaggerMainActivityComponent.factory().create(this)
    }

    private lateinit var currentVisibleFragment: Fragment

    private val fragmentsList = listOf<Fragment>(CharactersFeedFragment(),
        EpisodesFeedFragment())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityComponent.inject(this)
        setUpInternetConnectionObserver()
        createListFragments()
        setOnNavigationBarItemListener()

    }
    private fun createListFragments(){
        supportFragmentManager.commit {
            add(R.id.fragment_container, fragmentsList.first())
            add(R.id.fragment_container, fragmentsList[1])
            //TODO check if not null
            hide(fragmentsList[1])
            currentVisibleFragment = fragmentsList.first()
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
    override fun moveToDetailsFragment(container: Int, fragment: Fragment) {
        supportFragmentManager.commit {
            add(container, fragment)
            //TODO replace
            addToBackStack(fragment::javaClass.name)
            binding.bottomNavbar.visibility = View.GONE
            setReorderingAllowed(true)
        }
    }

    private fun setOnNavigationBarItemListener(){
        binding.bottomNavbar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.characters_navbar_button
                    ->showListFragment(fragmentsList.first())
                R.id.episode_navbar_button
                    ->showListFragment(fragmentsList[1])
            }
            true
        }

    }

    override fun removeUpperFragment() {
        binding.bottomNavbar.visibility = View.VISIBLE
        supportFragmentManager.popBackStack()
    }

    override fun showListFragment(fragment: Fragment) {
        if(fragment != currentVisibleFragment) {
            supportFragmentManager.commit {
                show(fragment)
                hide(currentVisibleFragment)
                currentVisibleFragment = fragment
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}