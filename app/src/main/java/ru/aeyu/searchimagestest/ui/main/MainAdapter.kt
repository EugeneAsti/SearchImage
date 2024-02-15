package ru.aeyu.searchimagestest.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.aeyu.searchimagestest.R
import ru.aeyu.searchimagestest.databinding.FoundItemBinding
import ru.aeyu.searchimagestest.domain.models.ImageItemDomain
import ru.aeyu.searchimagestest.ui.utils.ImageDiffUtils
import ru.aeyu.searchimagestest.ui.utils.getImageFromRemote

class MainAdapter(
    diffUtils: ImageDiffUtils,
    private val onItemClick: (clickedElement: ClickedElement, imageItem: ImageItemDomain) -> Unit
) : PagingDataAdapter<ImageItemDomain, MainAdapter.MainAdapterViewHolder>(diffUtils) {


    inner class MainAdapterViewHolder(
        private val binding: FoundItemBinding,
        private val onItemClick: (view: View, position: Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imageSearchResult.setOnClickListener {
                onItemClick(it, absoluteAdapterPosition)
            }
            binding.ibuttonShare.setOnClickListener {
                onItemClick(it, absoluteAdapterPosition)
            }
            binding.ibuttonToOriginal.setOnClickListener {
                onItemClick(it, absoluteAdapterPosition)
            }

        }

        fun bind(item: ImageItemDomain?) {
            if (item == null) return
            binding.imageSearchResult.getImageFromRemote(
                context = binding.imageSearchResult.context,
                url = item.thumbnail,
                progressBarWidget = binding.progressImageLoading
            )
        }
    }

    override fun onBindViewHolder(holder: MainAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapterViewHolder {
        val binding = FoundItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainAdapterViewHolder(binding, itemClick)
    }

    private val itemClick: (View, Int) -> Unit = { view, position ->
        val item: ImageItemDomain? = getItem(position)
        if (item != null) {
            val clickedElement: ClickedElement = when (view.id) {
                R.id.image_search_result -> ClickedElement.ITEM
                R.id.ibutton_share -> ClickedElement.SHARE
                R.id.ibutton_to_original -> ClickedElement.WEB
                else -> ClickedElement.ITEM
            }
            onItemClick(clickedElement, item)
        }
    }

    fun getAdapterItems(): List<ImageItemDomain> = snapshot().items

}

enum class ClickedElement {
    ITEM,
    SHARE,
    WEB,
}