package com.fab.happybeer.ui.beerlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.fab.happybeer.core.BaseViewHolder
import com.fab.happybeer.data.model.Beer
import com.fab.happybeer.databinding.BeerItemBinding
import com.fab.happybeer.ui.loadImage

class BeerListAdapter(
    private val itemClickListener: OnBeerClikListener
) : PagingDataAdapter<Beer, BaseViewHolder<*>>(BeerComparator) {

    interface OnBeerClikListener {
        fun onBeerListClick(beer: Beer)
        fun onFavoriteClick(beer: Beer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            BeerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = BeerListViewHolder(itemBinding)
        itemBinding.root.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onBeerListClick(getItem(position)!!)
        }
        itemBinding.imgFavorite.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onFavoriteClick(getItem(position)!!)
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is BeerListViewHolder -> holder.bind(getItem(position)!!)
        }
    }

    object BeerComparator : DiffUtil.ItemCallback<Beer>() {
        override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem == newItem
        }
    }

    private inner class BeerListViewHolder(
        val binding: BeerItemBinding
    ) :
        BaseViewHolder<Beer>(binding.root) {
        override fun bind(item: Beer) {
            binding.tvName.text = item.name
            binding.tvContributed.text = item.tagline
            item.image_url?.let {
                binding.imgBeer.loadImage(item.image_url)
            }
        }
    }
}