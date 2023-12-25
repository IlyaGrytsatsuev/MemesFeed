package com.example.rickandmortyapi.presenter.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.FragmentDetailsBinding
import com.example.rickandmortyapi.di.daggerComponents.CharacterDetailsFragmentComponent
import com.example.rickandmortyapi.di.daggerComponents.DaggerCharacterDetailsFragmentComponent
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.DetailsRecyclerAdapter
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.DetailsRecyclerItemDecorator
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.delegates.EpisodeListTitleItemDelegate
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.delegates.GenderParameterItemDelegate
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.delegates.LocationParameterItemDelegate
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.delegates.OriginParameterItemDelegate
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.delegates.SpeciesParameterItemDelegate
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.delegates.StatusParameterItemDelegate
import com.example.rickandmortyapi.presenter.State
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.EpisodesListItemDelegate
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerItemDelegate
import com.example.rickandmortyapi.presenter.viewmodels.CharacterDetailsViewModel
import com.example.rickandmortyapi.presenter.viewmodels.InternetConnectionObserverViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharacterDetailsFragment() : AbstractDetailsFragment() {

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel : CharacterDetailsViewModel
    by viewModels { viewModelFactory }

    override val internetObserverViewModel: InternetConnectionObserverViewModel
    by viewModels { viewModelFactory }


    private val component: CharacterDetailsFragmentComponent by lazy {
        DaggerCharacterDetailsFragmentComponent.factory().create(requireContext(),
            arguments?.getInt(CHARACTER_ID_PARAM, 0) ?: 0 )
    }

    override val delegates: List<RecyclerItemDelegate> by lazy {
        listOf(
            StatusParameterItemDelegate(),
            GenderParameterItemDelegate(),
            SpeciesParameterItemDelegate(),
            OriginParameterItemDelegate(),
            LocationParameterItemDelegate(),
            EpisodeListTitleItemDelegate(),
            EpisodesListItemDelegate()
        )
    }

    override val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder> by lazy {
        DetailsRecyclerAdapter(delegates)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)
        initializeCharacterDetailsRecycler()
        setUpDetailsStateObserver()
    }

    override fun setUpDetailsStateObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.curCharacter.collect{
                    when(it){
                        is State.Error ->
                            executeErrorState(it.data)

                        is State.Empty ->
                            executeEmptyState()

                        is State.Loading ->
                            executeLoadingState()
                        is State.Success ->
                            executeSuccessState(it.data)

                    }
                }
            }
        }
    }

    override fun reloadDetails() {
        //binding.detailsRecycler.scrollToPosition(0)
        viewModel.getCharacterDetails()
    }


    override fun executeErrorState(data: RecyclerModel?) {
        if(data != null)
            moveToAdapter(data)
        else
            showEmptyListMessage()
        showSnackBar(getString(R.string.error_message))
        hideProgressBar()
    }

    override fun executeEmptyState() {
        hideProgressBar()
        showEmptyListMessage()
    }

    override fun executeLoadingState() {
        showProgressBar()
    }

    override fun executeSuccessState(data: RecyclerModel?) {
        moveToAdapter(data)
        hideProgressBar()
        setUpCharacterImageAndName()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }



    private fun initializeCharacterDetailsRecycler(){
        binding.detailsRecycler.adapter = DetailsRecyclerAdapter(delegates)
        binding.detailsRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        binding.detailsRecycler
            .addItemDecoration(DetailsRecyclerItemDecorator())
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