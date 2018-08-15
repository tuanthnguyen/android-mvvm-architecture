package com.tuann.mvvm.presentation.images

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tuann.mvvm.databinding.FragmentImagesBinding
import com.tuann.mvvm.di.ViewModelFactory
import com.tuann.mvvm.presentation.Result
import com.tuann.mvvm.presentation.common.EndlessRecyclerOnScrollListener
import com.tuann.mvvm.presentation.common.RetryListener
import com.tuann.mvvm.util.AppExecutors
import com.tuann.mvvm.util.autoCleared
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class ImagesFragment : DaggerFragment(), RetryListener {
    private lateinit var binding: FragmentImagesBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var appExecutors: AppExecutors

    private var adapter by autoCleared<ImagesAdapter>()

    private val imagesViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ImagesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentImagesBinding.inflate(layoutInflater, container, false)
        binding.viewModel = imagesViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imagesViewModel.images.observe(this, Observer { result ->
            when (result) {
                is Result.Success -> {
                    adapter.submitList(result.data)
                    adapter.onFailure(false)
                }
                is Result.Failure -> {
                    adapter.onFailure(true)
                    Timber.wtf(result.errorMessage)
                }
            }
        })
        imagesViewModel.isLoading.observe(this, Observer { isLoading ->
            isLoading?.let {
                adapter.onFailure(!it)
            }
        })

        val adapter = ImagesAdapter(appExecutors, this) {
            Toast.makeText(activity, it.id, Toast.LENGTH_SHORT).show()
        }
        this.adapter = adapter
        (binding.recyclerView.layoutManager as GridLayoutManager).spanSizeLookup = object :
                GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    ImagesAdapter.MORE -> 2
                    else -> 1
                }
            }
        }

        // fix the bug: Auto scroll to bottom
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    binding.recyclerView.layoutManager?.scrollToPosition(0)
                }
            }
        })

        val scrollListener = object : EndlessRecyclerOnScrollListener(binding.recyclerView.layoutManager as GridLayoutManager) {
            override fun onLoadMore(currentPage: Int) {
                imagesViewModel.loadImages(currentPage)
            }
        }

        imagesViewModel.refresh.observe(this, Observer {
            scrollListener.reset()
        })

        binding.recyclerView.addOnScrollListener(scrollListener)

        binding.recyclerView.adapter = adapter
    }

    override fun retry() {
        imagesViewModel.retry()
    }

    companion object {
        fun newInstance(): ImagesFragment = ImagesFragment()
    }
}