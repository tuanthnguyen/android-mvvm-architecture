package com.tuann.mvvm.ui.common

import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.recyclerview.extensions.AsyncDifferConfig.Builder
import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.AdapterListUpdateCallback
import android.support.v7.util.DiffUtil.ItemCallback
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder

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
