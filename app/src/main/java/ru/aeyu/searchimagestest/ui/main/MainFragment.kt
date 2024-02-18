package ru.aeyu.searchimagestest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.aeyu.searchimagestest.R
import ru.aeyu.searchimagestest.databinding.FragmentMainBinding
import ru.aeyu.searchimagestest.domain.models.ContentItemDomain
import ru.aeyu.searchimagestest.ui.base.BaseFragment
import ru.aeyu.searchimagestest.ui.utils.Constants
import ru.aeyu.searchimagestest.ui.utils.ImageDiffUtils

class MainFragment : BaseFragment<MainState, MainEffect, FragmentMainBinding, MainViewModel>() {


    override val viewModel: MainViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var mainAdapter: MainAdapter

    override fun getBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Boolean
    ): FragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.images
        mainAdapter = MainAdapter(ImageDiffUtils(), onItemClick)
        recyclerView.adapter = mainAdapter
    }

    private val onItemClick: (clickedElement: ClickedElement, imageItem: ContentItemDomain) -> Unit =
        { clickedElement, imageItem ->
            when (clickedElement) {
                ClickedElement.ITEM -> {
                    val imagesArray = mainAdapter.getAdapterItems()
                    viewModel.onItemClicked(imagesArray, imageItem)
                }

                ClickedElement.SHARE -> {
                }

                ClickedElement.WEB -> {
                }
            }
        }

    override fun handleState(state: MainState) {
        showProgressIndicator(isVisible = state.isLoading)
        showEmptyListTextView(state.isVisibleMessageText, state.textMessage)
        mainAdapter.submitData(viewLifecycleOwner.lifecycle, state.pagingData)
    }

    private fun showEmptyListTextView(isVisible: Boolean, textMessage: String) {
        binding.textEmpty.isVisible = isVisible
        binding.textEmpty.text = textMessage
    }

    override fun handleEffects(effect: MainEffect) {
        when (effect) {
            is MainEffect.OnAlertMessage -> showErrorAlertDialog(effect.message)
            is MainEffect.OnImageClicked -> {
                // Я не сторонник передачи данных через Parcelable.
                // Я бы сохранил список в локальную БД, либо передал бы через общую viewModel, как проповедует developer.android.com - типа true way.
                // Но почему-то на всех собесах спрашивают как передать данные в другой фрагмент
                // и ответ в стиле true way их не устраивает. Видимо это имеют ввиду :(
                val argsBundle = Bundle()
                argsBundle.putParcelableArrayList(Constants.KEY_CONTENT_PARCEL, effect.images)
                argsBundle.putString(Constants.KEY_CURRENT_ITEM_URL, effect.item.original)
                argsBundle.putString(Constants.KEY_ITEM_TYPE, effect.currentItemTypeCode)
                findNavController().navigate(R.id.imagesCarouselFragment, argsBundle)
            }

            is MainEffect.OnShareClicked -> {
            }

            is MainEffect.OnWebClicked -> {
            }
        }
    }

    private fun showProgressIndicator(isVisible: Boolean) {
        binding.progressMain.isVisible = isVisible
    }
}