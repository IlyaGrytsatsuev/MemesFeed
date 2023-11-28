package com.example.rickandmortyapi.presenter.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.FragmentFiltersBinding
import com.example.rickandmortyapi.di.MyApp
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus
import javax.inject.Inject
import javax.inject.Named


class FiltersFragment : Fragment(R.layout.fragment_filters) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    //private val filteredFeedViewModel: FilteredFeedViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentFiltersBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApp).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFiltersBinding.bind(view)
        setUpStatusSpinner()
        setUpGenderSpinner()
    }

    private fun setUpStatusSpinner(){
        val statusArray = resources.getStringArray(R.array.status_values_array)
        val adapter = ArrayAdapter(requireContext()
            , android.R.layout.simple_spinner_item, statusArray)
        binding.statusSpinner.onItemSelectedListener = onStatusSelectedListener()
        binding.statusSpinner.adapter = adapter


    }

    private fun onStatusSelectedListener() =
        object : AdapterView.OnItemSelectedListener{
            val statusObjectsList = listOf(CharacterStatus.UNCHOSEN, CharacterStatus.ALIVE,
                CharacterStatus.DEAD, CharacterStatus.UNKNOWN)
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //filteredFeedViewModel.setCharacterStatus(statusObjectsList[p2])
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

    private fun onGenderSelectedListener() =
        object : AdapterView.OnItemSelectedListener{
            val genderObjectsList = listOf(
                CharacterGender.UNCHOSEN, CharacterGender.FEMALE,
                CharacterGender.MALE, CharacterGender.GENDERLESS, CharacterGender.UNKNOWN)
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //filteredFeedViewModel.setCharacterGender(genderObjectsList[p2])
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

}