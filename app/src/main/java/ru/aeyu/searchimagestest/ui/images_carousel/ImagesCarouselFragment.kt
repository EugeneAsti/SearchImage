package ru.aeyu.searchimagestest.ui.images_carousel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import ru.aeyu.searchimagestest.databinding.FragmentImagesCarouselBinding
import ru.aeyu.searchimagestest.ui.base.BaseFragment
import kotlin.math.abs

class ImagesCarouselFragment :
    BaseFragment<CarouselState, CarouselEffects, FragmentImagesCarouselBinding, CarouselViewModel>() {
    override val viewModel: CarouselViewModel by viewModels()

    private lateinit var imagesPager: ViewPager2
    private lateinit var carouselAdapter: CarouselAdapter
    private val extArgs: ImagesCarouselFragmentArgs by navArgs()

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
        viewModel.onFragmentCreated(extArgs.imagesUrls, extArgs.currentImage)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveCurrentPosition(imagesPager.currentItem)
    }

    private fun init() {
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
        if (state.isShowMessageText) {
            binding.tvSomeInformationText.isVisible = true
            binding.tvSomeInformationText.text = state.messageText
        } else
            binding.tvSomeInformationText.isVisible = false

        carouselAdapter.differ.submitList(state.imagesList)
        if (state.currentImagePos >= 0)
            imagesPager.setCurrentItem(state.currentImagePos, false)
    }

    override fun handleEffects(effect: CarouselEffects) {
        when (effect) {
            is CarouselEffects.OnAlertMessage -> showErrorAlertDialog(effect.message)
        }
    }
}