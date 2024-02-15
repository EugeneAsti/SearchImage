package ru.aeyu.searchimagestest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.aeyu.searchimagestest.R
import ru.aeyu.searchimagestest.databinding.FragmentMainBinding
import ru.aeyu.searchimagestest.domain.enums.ContentTypes
import ru.aeyu.searchimagestest.domain.enums.Countries
import ru.aeyu.searchimagestest.domain.enums.Languages
import ru.aeyu.searchimagestest.domain.enums.MenuContentSizes
import ru.aeyu.searchimagestest.domain.models.ImageItemDomain
import ru.aeyu.searchimagestest.domain.models.SearchFilter
import ru.aeyu.searchimagestest.ui.base.BaseFragment
import ru.aeyu.searchimagestest.ui.utils.ImageDiffUtils
import ru.aeyu.searchimagestest.ui.utils.MainMenuProvider

class MainFragment : BaseFragment<MainState, MainEffect, FragmentMainBinding, MainViewModel>() {


    override val viewModel: MainViewModel by activityViewModels()
    private lateinit var menuHost: MenuHost

    private lateinit var recyclerView: RecyclerView
    private lateinit var mainAdapter: MainAdapter
    private lateinit var myMenuProvider: MainMenuProvider
    private lateinit var filter: SearchFilter
    override fun getBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Boolean
    ): FragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuHost = requireActivity()

        filter = viewModel.currentSearchFilter
        myMenuProvider = MainMenuProvider(requireContext(), filter, onMenuItemSelected) {
            requireActivity().invalidateOptionsMenu()
        }

        menuHost.addMenuProvider(myMenuProvider, requireActivity())
        recyclerView = binding.images
        mainAdapter = MainAdapter(ImageDiffUtils(), onItemClick)
        recyclerView.adapter = mainAdapter
    }

    private val onItemClick: (clickedElement: ClickedElement, imageItem: ImageItemDomain) -> Unit =
        { clickedElement, imageItem ->
            when (clickedElement) {
                ClickedElement.ITEM -> {
                    menuHost.removeMenuProvider(myMenuProvider)
                    val imagesArray = mainAdapter.getAdapterItems()
                    val images: List<String> = imagesArray.map { it.original }
                    val action = MainFragmentDirections.imagesCarouselFragmentAction(
                        images.toTypedArray(),
                        imageItem.original
                    )

                    findNavController().navigate(action)
                }

                ClickedElement.SHARE -> {
                }

                ClickedElement.WEB -> {
                }
            }
        }

//    private val myMenuProvider = object :

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

    private val onMenuItemSelected: (menuItem: MenuItem) -> Boolean = { menuItem ->
        when (menuItem.itemId) {
            R.id.item_images -> {
                menuItem.isChecked = true
                viewModel.onContentTypeChange(ContentTypes.IMAGES)
                true
            }

            R.id.item_videos -> {
                menuItem.isChecked = true
                viewModel.onContentTypeChange(ContentTypes.VIDEOS)
                true
            }

            R.id.item_size_any -> {
                menuItem.isChecked = true
                viewModel.onContentSizeChange(MenuContentSizes.ANY)
                true
            }

            R.id.item_size_large -> {
                menuItem.isChecked = true
                viewModel.onContentSizeChange(MenuContentSizes.LARGE)
                true
            }

            R.id.item_size_medium -> {
                menuItem.isChecked = true
                viewModel.onContentSizeChange(MenuContentSizes.MEDIUM)
                true
            }

            R.id.item_size_small -> {
                menuItem.isChecked = true
                viewModel.onContentSizeChange(MenuContentSizes.SMALL)
                true
            }

            R.id.item_size_icon -> {
                menuItem.isChecked = true
                viewModel.onContentSizeChange(MenuContentSizes.ICON)
                true
            }

            R.id.item_country_any -> {
                menuItem.isChecked = true
                viewModel.onCountryResultChange(Countries.ANY)
                true
            }

            R.id.item_country_australia -> {
                menuItem.isChecked = true
                viewModel.onCountryResultChange(Countries.AUSTRALIA)
                true
            }

            R.id.item_country_germany -> {
                menuItem.isChecked = true
                viewModel.onCountryResultChange(Countries.GERMANY)
                true
            }

            R.id.item_country_russia -> {
                menuItem.isChecked = true
                viewModel.onCountryResultChange(Countries.RUSSIA)
                true
            }

            R.id.item_country_spain -> {
                menuItem.isChecked = true
                viewModel.onCountryResultChange(Countries.SPAIN)
                true
            }

            R.id.item_language_any -> {
                menuItem.isChecked = true
                viewModel.onLanguageResultChange(Languages.ANY)
                true
            }

            R.id.item_language_german -> {
                menuItem.isChecked = true
                viewModel.onLanguageResultChange(Languages.GERMAN)
                true
            }

            R.id.item_language_ru -> {
                menuItem.isChecked = true
                viewModel.onLanguageResultChange(Languages.RUSSIAN)
                true
            }

            R.id.item_language_spanish -> {
                menuItem.isChecked = true
                viewModel.onLanguageResultChange(Languages.SPANISH)
                true
            }

            else -> false
        }
    }
}