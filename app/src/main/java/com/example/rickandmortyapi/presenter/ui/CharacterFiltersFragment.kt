package com.example.rickandmortyapi.presenter.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.FragmentFiltersBinding
import com.example.rickandmortyapi.di.daggerComponents.MainActivityComponent
import com.example.rickandmortyapi.presenter.viewmodels.CharactersFeedViewModel
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharacterFiltersFragment : Fragment(R.layout.fragment_filters) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CharactersFeedViewModel by viewModels {viewModelFactory}

    private lateinit var binding: FragmentFiltersBinding

    private val statusObjectsList = listOf(CharacterStatus.UNCHOSEN, CharacterStatus.ALIVE,
        CharacterStatus.DEAD, CharacterStatus.UNKNOWN)

    private val genderObjectsList = listOf(
        CharacterGender.UNCHOSEN, CharacterGender.FEMALE,
        CharacterGender.MALE, CharacterGender.GENDERLESS, CharacterGender.UNKNOWN)

    private val component: MainActivityComponent by lazy {
        (activity as MainActivity).activityComponent
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFiltersBinding.bind(view)
        setUpStatusSpinner()
        setUpStatusFilterObserver()
        setUpGenderSpinner()
        setUpGenderFilterObserver()
        setUpOnCloseListener()
    }

    private fun setUpStatusSpinner(){
        val statusArray = resources.getStringArray(R.array.status_values_array)
        val adapter = ArrayAdapter(requireContext()
            , android.R.layout.simple_spinner_item, statusArray)
        binding.statusSpinner.onItemSelectedListener = onStatusSelectedListener()
        binding.statusSpinner.adapter = adapter
    }

    private fun setUpStatusFilterObserver(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characterStatusFilter.collect {
                    binding.statusSpinner
                        .setSelection(statusObjectsList.indexOf(it))
                }
            }
        }
    }
    private fun onStatusSelectedListener() =
        object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.setCharacterStatusFilter(statusObjectsList[p2])
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

    private fun setUpGenderSpinner(){
        val genderArray = resources.getStringArray(R.array.gender_values_array)
        val adapter = ArrayAdapter(requireContext()
            , android.R.layout.simple_spinner_item, genderArray)
        binding.genderSpinner.onItemSelectedListener = onGenderSelectedListener()
        binding.genderSpinner.adapter = adapter
    }

    private fun setUpGenderFilterObserver(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.characterGenderFilter.collect {
                    binding.genderSpinner
                        .setSelection(genderObjectsList.indexOf(it))
                }
            }
        }
    }

    private fun setUpOnCloseListener(){
        binding.closeButton.setOnClickListener {
            (activity as MainActivity)
                .handleOnBackPressedNavigation()
        }
    }
    private fun onGenderSelectedListener() =
        object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.setCharacterGenderFilter(genderObjectsList[p2])
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

}