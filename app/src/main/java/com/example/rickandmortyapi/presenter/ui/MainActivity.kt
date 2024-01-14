package com.example.rickandmortyapi.presenter.ui

import android.os.Bundle
import android.util.Log
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
import com.example.rickandmortyapi.di.daggerComponents.DaggerEpisodesFeedFragmentComponent
import com.example.rickandmortyapi.di.daggerComponents.DaggerMainActivityComponent
import com.example.rickandmortyapi.di.daggerComponents.EpisodesFeedFragmentComponent
import com.example.rickandmortyapi.di.daggerComponents.MainActivityComponent
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.FragmentNavigator
import com.example.rickandmortyapi.presenter.viewmodels.CharactersFeedViewModel
import com.example.rickandmortyapi.presenter.viewmodels.InternetConnectionObserverViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentNavigator {

    private lateinit var binding: ActivityMainBinding


    private val viewModel: InternetConnectionObserverViewModel by viewModels{viewModelFactory}

    private val feedViewModel: CharactersFeedViewModel by viewModels{viewModelFactory}

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val activityComponent: MainActivityComponent by lazy {
        DaggerMainActivityComponent.factory().create(this)
    }


    private val fragmentsList = listOf<Fragment>(CharactersFeedFragment(),
        EpisodesFeedFragment())

    private val recyclerFragmentsIconsIds: Map<Fragment, Int>
    = mapOf(Pair(fragmentsList.first(), R.id.characters_navbar_button),
        Pair(fragmentsList[1], R.id.episode_navbar_button))

    private  var currentVisibleFragment: Fragment = fragmentsList.first()
    private  var previousVisibleFragment: Fragment = currentVisibleFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityComponent.inject(this)
        setUpInternetConnectionObserver()
        addInitialListFragment()
        setOnNavigationBarItemListener()

    }
    private fun addInitialListFragment(){
        supportFragmentManager.commit {
            add(R.id.fragment_container, fragmentsList.first())
            currentVisibleFragment = fragmentsList.first()
            setReorderingAllowed(true)
        }
    }

    private fun setUpInternetConnectionObserver(){
        viewModel.getInitialNetworkStatus()
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


    private fun showSnackBar(message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
    override fun moveToChildFragment(container: Int, fragment: Fragment) {
            if(currentVisibleFragment.childFragmentManager.backStackEntryCount > 0)
                currentVisibleFragment.childFragmentManager.popBackStack()

            currentVisibleFragment.childFragmentManager.commit {
                replace(container, fragment)
                addToBackStack(fragment::javaClass.name)
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

    override fun showListFragment(fragment: Fragment) {
        if(fragment != currentVisibleFragment) {
            supportFragmentManager.commit {
                if(!fragment.isAdded) {
                    replace(R.id.fragment_container, fragment)
                    addToBackStack(fragment::javaClass.name)
                    setReorderingAllowed(true)
                    Log.d("netlist", "fragment creation")
                }

                show(fragment)

                hide(currentVisibleFragment)

                previousVisibleFragment = currentVisibleFragment
                currentVisibleFragment = fragment

            }
        }
    }

    override fun onBackPressed() {
        handleOnBackPressedNavigation()
    }



    override fun handleOnBackPressedNavigation(){
        when{
            currentVisibleFragment.childFragmentManager.backStackEntryCount > 0 ->
                currentVisibleFragment.childFragmentManager.popBackStack()

            currentVisibleFragment == fragmentsList.firstOrNull() ->{
                finish()
                super.onBackPressed()
            }
            else->{
                supportFragmentManager.popBackStack()
                binding.bottomNavbar.selectedItemId =
                    recyclerFragmentsIconsIds[previousVisibleFragment]
                        ?:R.id.characters_navbar_button
            }
        }
    }
}