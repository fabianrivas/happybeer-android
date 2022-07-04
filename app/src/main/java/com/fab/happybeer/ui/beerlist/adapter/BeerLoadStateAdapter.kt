package com.fab.happybeer.ui.beerlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fab.happybeer.databinding.ItemLoadingStateBinding
import com.fab.happybeer.ui.show
import com.fab.happybeer.ui.visible

class BeerLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<BeerLoadStateAdapter.LoaderViewHolder>() {

    override fun onBindViewHolder(
        holder: BeerLoadStateAdapter.LoaderViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoaderViewHolder(
            ItemLoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            retry
        )

    inner class LoaderViewHolder(
        private val binding: ItemLoadingStateBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.tvError.text = loadState.error.localizedMessage
            }
            binding.progressbar.visible(loadState is LoadState.Loading)
            binding.btnRetry.visible(loadState is LoadState.Error)
            binding.tvError.visible(loadState is LoadState.Error)
            binding.btnRetry.setOnClickListener {
                retry()
            }
            binding.progressbar.show()
        }
    }
}