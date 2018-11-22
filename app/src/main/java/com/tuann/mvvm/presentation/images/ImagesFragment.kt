package com.tuann.mvvm.presentation.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuann.mvvm.R
import com.tuann.mvvm.databinding.FragmentImagesBinding
import com.tuann.mvvm.di.ViewModelFactory
import com.tuann.mvvm.presentation.Result
import com.tuann.mvvm.presentation.view.EndlessRecyclerOnScrollListener
import com.tuann.mvvm.presentation.common.RetryListener
import com.tuann.mvvm.presentation.view.GridSpacingItemDecoration
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
        initRecyclerView()
        imagesViewModel.images.observe(viewLifecycleOwner, Observer { result ->
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
        imagesViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading.let {
                adapter.onFailure(!it)
            }
        })
    }

    private fun initRecyclerView() {
        val adapter = ImagesAdapter(appExecutors, this) {
            Toast.makeText(requireContext(), it.id, Toast.LENGTH_SHORT).show()
        }
        this.adapter = adapter
        // fix the bug: Auto scroll to bottom
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    binding.recyclerView.layoutManager?.scrollToPosition(0)
                }
            }
        })
        val layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
        binding.recyclerView.layoutManager = layoutManager
        val scrollListener = object : EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(currentPage: Int) {
                // We may not call imagesViewModel.loadImages(1), this func will be called because
                // the list has more item which always showed
                // so I changed the `current page` in `EndlessRecyclerOnScrollListener` to 0
                imagesViewModel.loadImages(currentPage)
            }
        }
        imagesViewModel.refresh.observe(viewLifecycleOwner, Observer {
            scrollListener.reset()
        })
        val spacing = resources.getDimensionPixelSize(R.dimen.spacing_tiny)
        binding.recyclerView.addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, spacing, true))
        binding.recyclerView.addOnScrollListener(scrollListener)
        binding.recyclerView.adapter = adapter
    }

    override fun retry() {
        imagesViewModel.retry()
    }

    companion object {
        private const val SPAN_COUNT = 2

        fun newInstance(): ImagesFragment = ImagesFragment()
    }
}