package com.tuann.mvvm.presentation.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.tuann.mvvm.R
import com.tuann.mvvm.data.model.Image
import com.tuann.mvvm.databinding.ItemImageBinding
import com.tuann.mvvm.databinding.ItemLoadMoreBinding
import com.tuann.mvvm.presentation.common.DataBoundListCustomAdapter
import com.tuann.mvvm.presentation.common.RetryListener
import com.tuann.mvvm.util.AppExecutors

class ImagesAdapter(
    appExecutors: AppExecutors,
    private val retryListener: RetryListener,
    private val callback: ((Image) -> Unit)?
) : DataBoundListCustomAdapter<Image, ViewDataBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem == newItem
            }
        }
) {
    // better enums class
    companion object {
        private const val ITEM = 1
        const val MORE = 2
    }

    private var isError = false

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutId = when (viewType) {
            ITEM -> R.layout.item_image
            else -> R.layout.item_load_more
        }
        return DataBindingUtil.inflate(
                layoutInflater, layoutId,
                parent,
                false)
    }

    override fun bind(binding: ViewDataBinding, item: Image) {
        when (binding) {
            is ItemImageBinding -> {
                binding.root.setOnClickListener {
                    binding.image?.let {
                        callback?.invoke(it)
                    }
                }
                binding.image = item
            }
            is ItemLoadMoreBinding -> {
                binding.retry = retryListener
                binding.isError = isError
            }
        }
    }

    fun onFailure(isError: Boolean) {
        this.isError = isError
        notifyItemChanged(itemCount - 1)
    }

    override fun getItemCount() = getCurrentList().size + 1

    override fun getItem(position: Int): Image =
            getCurrentList().getOrElse(position) { Image() }

    override fun getItemViewType(position: Int) =
            if (position == itemCount - 1) MORE else ITEM
}