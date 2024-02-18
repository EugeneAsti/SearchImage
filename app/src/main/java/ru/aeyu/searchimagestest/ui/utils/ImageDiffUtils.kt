package ru.aeyu.searchimagestest.ui.utils

import androidx.recyclerview.widget.DiffUtil
import ru.aeyu.searchimagestest.domain.models.ContentItemDomain

class ImageDiffUtils : DiffUtil.ItemCallback<ContentItemDomain>() {
    override fun areItemsTheSame(
        oldItem: ContentItemDomain,
        newItem: ContentItemDomain
    ): Boolean {
        return oldItem.original == newItem.original
    }

    override fun areContentsTheSame(
        oldItem: ContentItemDomain,
        newItem: ContentItemDomain
    ): Boolean {
        return oldItem.original == newItem.original
    }
}