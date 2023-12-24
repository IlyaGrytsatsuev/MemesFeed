package com.example.rickandmortyapi.presenter.commonRecyclerUtils

import androidx.fragment.app.Fragment

interface FragmentNavigator {
    fun moveToDetailsFragment(container: Int, fragment:Fragment)

    fun removeUpperFragment()

    fun showListFragment(fragment: Fragment)


}