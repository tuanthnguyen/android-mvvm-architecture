package com.tuann.mvvm.presentation.view

import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncDifferConfig.Builder
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

@Suppress("LeakingThis")
abstract class ListAdapterCustom<T, VH : ViewHolder> : Adapter<VH> {
    private val helper: AsyncListDiffer<T>

    protected constructor(diffCallback: ItemCallback<T>) {
        this.helper = AsyncListDiffer(AdapterListUpdateCallback(this), Builder(diffCallback).build())
    }

    protected constructor(config: AsyncDifferConfig<T>) {
        this.helper = AsyncListDiffer(AdapterListUpdateCallback(this), config)
    }

    fun submitList(list: List<T>?) {
        this.helper.submitList(list)
    }

    open fun getItem(position: Int): T =
            this.helper.currentList[position]

    override fun getItemCount(): Int =
            this.helper.currentList.size

    fun getCurrentList(): MutableList<T> = this.helper.currentList
}