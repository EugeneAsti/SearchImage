package ru.aeyu.searchimagestest.ui.utils

import androidx.recyclerview.widget.DiffUtil
import ru.aeyu.searchimagestest.domain.models.ImageItemDomain

class ImageDiffUtils : DiffUtil.ItemCallback<ImageItemDomain>() {
    override fun areItemsTheSame(
        oldItem: ImageItemDomain,
        newItem: ImageItemDomain
    ): Boolean {
        return oldItem.original == newItem.original
    }

    override fun areContentsTheSame(
        oldItem: ImageItemDomain,
        newItem: ImageItemDomain
    ): Boolean {
        return oldItem.original == newItem.original
    }
}