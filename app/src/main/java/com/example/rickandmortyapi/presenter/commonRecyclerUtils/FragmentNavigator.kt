package com.example.rickandmortyapi.presenter.commonRecyclerUtils

import androidx.fragment.app.Fragment

interface FragmentNavigator {
    fun moveToChildFragment(container: Int, fragment:Fragment)

    fun handleOnBackPressedNavigation()

    fun showListFragment(fragment: Fragment)


}