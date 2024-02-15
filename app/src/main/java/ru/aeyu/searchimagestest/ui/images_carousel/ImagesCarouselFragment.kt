package ru.aeyu.searchimagestest.ui.images_carousel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import ru.aeyu.searchimagestest.databinding.FragmentImagesCarouselBinding
import ru.aeyu.searchimagestest.ui.base.BaseFragment
import kotlin.math.abs

class ImagesCarouselFragment :
    BaseFragment<CarouselState, CarouselEffects, FragmentImagesCarouselBinding, CarouselViewModel>() {
    override val viewModel: CarouselViewModel by activityViewModels()

    private lateinit var viewPager2: ViewPager2
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

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (!findNavController().popBackStack()) {
                requireActivity().finish()
            }
        }
    }

    private fun init(){
        viewPager2 = binding.pagerImages
        viewModel.generateImagesList(extArgs.imagesUrls)
        carouselAdapter = CarouselAdapter(carouselAdapterLimitReached)

        viewPager2.adapter = carouselAdapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewPager2.setPageTransformer(transformer)
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
    }

    override fun handleEffects(effect: CarouselEffects) {
        when (effect) {
            is CarouselEffects.OnAlertMessage -> showErrorAlertDialog(effect.message)
        }
    }
}