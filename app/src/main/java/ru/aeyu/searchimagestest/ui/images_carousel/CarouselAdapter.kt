package ru.aeyu.searchimagestest.ui.images_carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.aeyu.searchimagestest.databinding.ImagesCarouselItemBinding
import ru.aeyu.searchimagestest.ui.utils.getImageFromRemote

class CarouselAdapter(
    private val onLimitPageReached: () -> Unit
) : RecyclerView.Adapter<CarouselAdapter.ImageViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    inner class ImageViewHolder(
        private val binding: ImagesCarouselItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String?) {
            if (item == null) return
            binding.ivCurrentImage.getImageFromRemote(
                context = binding.ivCurrentImage.context,
                url = item,
                progressBarWidget = binding.progressImageViewLoading
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImagesCarouselItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        if (position == differ.currentList.size - 1)
            onLimitPageReached()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}