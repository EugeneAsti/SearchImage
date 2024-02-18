package ru.aeyu.searchimagestest.ui.images_carousel

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import ru.aeyu.searchimagestest.R
import ru.aeyu.searchimagestest.databinding.FragmentImagesCarouselBinding
import ru.aeyu.searchimagestest.domain.enums.ContentTypes
import ru.aeyu.searchimagestest.domain.models.ContentItemDomain
import ru.aeyu.searchimagestest.ui.base.BaseFragment
import ru.aeyu.searchimagestest.ui.utils.Constants
import kotlin.math.abs

class ImagesCarouselFragment :
    BaseFragment<CarouselState, CarouselEffects, FragmentImagesCarouselBinding, CarouselViewModel>() {

    override val viewModel: CarouselViewModel by viewModels()
    private lateinit var imagesPager: ViewPager2
    private lateinit var carouselAdapter: CarouselAdapter
    private lateinit var menuHost: MenuHost
    private lateinit var imagesMenuProvider: ImagesMenuProvider

    override fun getBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Boolean
    ): FragmentImagesCarouselBinding {
        return FragmentImagesCarouselBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        val images = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelableArrayList(
                Constants.KEY_CONTENT_PARCEL,
                ContentItemDomain::class.java
            )
        } else {
            val classLoader = ClassLoader.getSystemClassLoader()
            classLoader.loadClass(ContentItemDomain::class.java.name)
            arguments?.classLoader = classLoader
            arguments?.getParcelableArrayList<ContentItemDomain>(Constants.KEY_CONTENT_PARCEL)
        }
        val currentImage = arguments?.getString(Constants.KEY_CURRENT_ITEM_URL, "") ?: ""
        val contentTypeCode = arguments?.getString(Constants.KEY_ITEM_TYPE, "") ?: ""
        viewModel.onFragmentCreated(images, currentImage, contentTypeCode)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveCurrentPosition(imagesPager.currentItem)
    }

    private fun init() {
        imagesMenuProvider = ImagesMenuProvider(requireContext(), onMenuSelected)
        menuHost = requireActivity()
        menuHost.addMenuProvider(imagesMenuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
        imagesPager = binding.pagerImages
        carouselAdapter = CarouselAdapter(carouselAdapterLimitReached)
        imagesPager.adapter = carouselAdapter
        imagesPager.offscreenPageLimit = 3

        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        imagesPager.setPageTransformer(transformer)
    }

    private val carouselAdapterLimitReached: () -> Unit = {

    }

    override fun handleState(state: CarouselState) {
        showLoadingProgress(state.isLoading)
        if (state.isShowMessageText) {
            binding.tvSomeInformationText.isVisible = true
            binding.tvSomeInformationText.text = state.messageText
        } else
            binding.tvSomeInformationText.isVisible = false

        carouselAdapter.differ.submitList(state.imagesList)
        if (state.currentImagePos >= 0)
            imagesPager.setCurrentItem(state.currentImagePos, false)
    }

    private fun showLoadingProgress(isLoading: Boolean) {
        binding.progressAppBar.isVisible = isLoading
    }

    override fun handleEffects(effect: CarouselEffects) {
        when (effect) {
            is CarouselEffects.OnAlertMessage -> showErrorAlertDialog(effect.message)
            is CarouselEffects.OnToastMessage -> showToast(effect.message)
            is CarouselEffects.OnShareResource -> showSharingApi(
                effect.contentType,
                effect.contentUri,
                effect.contentThumbnailUri
            )
        }
    }

    private fun showSharingApi(
        contentType: ContentTypes,
        contentUri: Uri,
        contentThumbnailUri: Uri
    ) {
        val mimeContentType = when (contentType) {
            ContentTypes.IMAGES -> "image/jpeg"
            ContentTypes.VIDEOS -> "video/*"
        }

        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, contentUri.path)
            putExtra(Intent.EXTRA_TITLE, "Sharing content")
//            setDataAndType(contentUri, mimeContentType)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            putExtra(Intent.EXTRA_STREAM, contentUri)
            type = mimeContentType

        }, null)
        startActivity(share)
    }

    private val onMenuSelected: (menuItem: MenuItem) -> Boolean = { menuItem ->
        when (menuItem.itemId) {
            R.id.item_share -> {
                viewModel.onSharedButtonClicked(requireContext(), imagesPager.currentItem)
                true
            }

            R.id.item_home_page -> {
                true
            }

            else -> {
                false
            }
        }
    }
}