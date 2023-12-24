package com.example.rickandmortyapi.presenter.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.FragmentCharacterDetailsBinding
import com.example.rickandmortyapi.di.daggerComponents.CharacterDetailsFragmentComponent
import com.example.rickandmortyapi.di.daggerComponents.DaggerCharacterDetailsFragmentComponent
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.EpisodesListItemDelegate
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.CharacterDetailsRecyclerItemDecorator
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.DetailsRecyclerAdapter
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.delegates.EpisodeListTitleItemDelegate
import javax.inject.Inject
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.delegates.GenderParameterItemDelegate
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.delegates.LocationParameterItemDelegate
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.delegates.OriginParameterItemDelegate
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.delegates.SpeciesParameterItemDelegate
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.delegates.StatusParameterItemDelegate
import com.example.rickandmortyapi.presenter.State
import com.example.rickandmortyapi.presenter.viewmodels.CharacterDetailsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    private lateinit var binding: FragmentCharacterDetailsBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel : CharacterDetailsViewModel
    by viewModels { viewModelFactory }

    private var snackBar: Snackbar? = null

    private var recyclerIsInitialized = false

    private val component: CharacterDetailsFragmentComponent by lazy {
        DaggerCharacterDetailsFragmentComponent.factory().create(requireContext(),
            arguments?.getInt(CHARACTER_ID_PARAM, 0) ?: 0 )
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterDetailsBinding.bind(view)
        initializeCharacterDetailsRecycler()
        setUpCurCharacterObserver()
    }

    private fun initializeCharacterDetailsRecycler(){
        val delegates = mutableListOf(
            StatusParameterItemDelegate(),
            GenderParameterItemDelegate(),
            SpeciesParameterItemDelegate(),
            OriginParameterItemDelegate(),
            LocationParameterItemDelegate(),
            EpisodeListTitleItemDelegate(),
            EpisodesListItemDelegate()
        )
        binding.detailsRecycler.adapter = DetailsRecyclerAdapter(delegates)
        binding.detailsRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        binding.detailsRecycler
            .addItemDecoration(CharacterDetailsRecyclerItemDecorator())
    }

    private fun setUpCharacterImageAndName(){
        Glide.with(requireContext())
            .load((viewModel.curCharacter.value.data
                    as CharacterDetailsModel).image)
            .placeholder(R.drawable.glide_placeholder)
            .centerCrop()
            .into(binding.characterImage)
        binding.characterName.text = (viewModel.curCharacter.value.data
                as CharacterDetailsModel).name
    }
    private fun setUpCurCharacterObserver(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.curCharacter.collect{
                    when(it){
                        is State.Error ->{
                            if(it.data != null)
                                moveToAdapter(it.data)
                            else
                                showEmptyListMessage()
                            showSnackBar(getString(R.string.error_message))
                            hideProgressBar()
                        }
                        is State.Empty ->{
                            hideProgressBar()
                            showEmptyListMessage()
                        }
                        is State.Loading -> showProgressBar()
                        is State.Success -> {
                            moveToAdapter(it.data)
                            hideProgressBar()
                            setUpCharacterImageAndName()
                        }
                    }
                }
            }
        }
    }

    private fun moveToAdapter(data : RecyclerModel?){
        data?.let {
            (binding.detailsRecycler.adapter as DetailsRecyclerAdapter)
                .setCharacterDetailsModel(it)
        }
    }

    private fun showProgressBar(){
        binding.detailsProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.detailsProgressBar.visibility = View.GONE
        hideEmptyListMessage()
    }

    private fun showSnackBar(message: String){
        snackBar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        snackBar?.show()
    }

    private fun showEmptyListMessage(){
        binding.emptyStateTextView.visibility = View.VISIBLE
    }
    private fun hideEmptyListMessage(){
        binding.emptyStateTextView.visibility = View.GONE
    }

    companion object {
        private const val CHARACTER_ID_PARAM = "CHARACTER_ID_PARAM"
        fun newInstance(characterId: Int):CharacterDetailsFragment =
            CharacterDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(CHARACTER_ID_PARAM, characterId)
                }
            }
    }
}