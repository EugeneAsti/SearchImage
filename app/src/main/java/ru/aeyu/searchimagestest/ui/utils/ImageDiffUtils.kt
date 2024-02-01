package ru.aeyu.searchimagestest.ui.utils

import androidx.recyclerview.widget.DiffUtil
import ru.aeyu.searchimagestest.data.remote.model.ImageItem

class ImageDiffUtils : DiffUtil.ItemCallback<ImageItem>() {
    override fun areItemsTheSame(
        oldItem: ImageItem,
        newItem: ImageItem
    ): Boolean {
        return oldItem.original == newItem.original
    }

    override fun areContentsTheSame(
        oldItem: ImageItem,
        newItem: ImageItem
    ): Boolean {
        return oldItem.original == newItem.original
    }
}