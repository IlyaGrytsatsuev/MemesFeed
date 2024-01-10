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
import com.example.rickandmortyapi.di.daggerComponents.DaggerMainActivityComponent
import com.example.rickandmortyapi.di.daggerComponents.MainActivityComponent
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.FragmentNavigator
import com.example.rickandmortyapi.presenter.viewmodels.InternetConnectionObserverViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO search Fragment
class MainActivity : AppCompatActivity(), FragmentNavigator {

    private lateinit var binding: ActivityMainBinding


    private val viewModel: InternetConnectionObserverViewModel by viewModels{viewModelFactory}

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val activityComponent: MainActivityComponent by lazy {
        DaggerMainActivityComponent.factory().create(this)
    }

    private val fragmentsList = listOf<Fragment>(CharactersFeedFragment(),
        EpisodesFeedFragment())

    private val recyclerFragmentsDetailsStates: Map<Fragment, Int>
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
            //TODO check if not null
            //addToBackStack(fragmentsList.first()::javaClass.name)
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

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun showSnackBar(message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
    override fun moveToChildFragment(container: Int, fragment: Fragment) {
            currentVisibleFragment.childFragmentManager.commit {
                replace(container, fragment)
//                if(currentVisibleFragment.childFragmentManager.backStackEntryCount > 0)
//                    currentVisibleFragment.childFragmentManager.popBackStack()
                addToBackStack(fragment::javaClass.name)
                setReorderingAllowed(true)
            }
    }

    override fun showFiltrationFragment(container: Int, fragment: Fragment) {
        supportFragmentManager.commit {
            add(container, fragment)
            addToBackStack(fragment::javaClass.name)
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

                //TODO white screen after onBackPressed call and then switching
                // to popped fragment if show is not called question?
                hide(currentVisibleFragment)

                previousVisibleFragment = currentVisibleFragment
                currentVisibleFragment = fragment

            }
        }
    }

    override fun onBackPressed() {
        handleOnBackPressedNavigation()
    }


    fun popUpFilterFragment(){
        currentVisibleFragment.childFragmentManager.popBackStack()
    }

    override fun handleOnBackPressedNavigation(){
        when{
            currentVisibleFragment.childFragmentManager.backStackEntryCount > 0 ->
                currentVisibleFragment.childFragmentManager.popBackStack()

            currentVisibleFragment == fragmentsList.firstOrNull() ->{
                supportFragmentManager.popBackStack()
                super.onBackPressed()
            }
            else->{
                supportFragmentManager.popBackStack()
                binding.bottomNavbar.selectedItemId =
                    recyclerFragmentsDetailsStates[previousVisibleFragment]
                        ?:R.id.characters_navbar_button
                //TODO map nullable Int question
            }
        }
    }
}