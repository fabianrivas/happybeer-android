package com.fab.happybeer.ui.favoritelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fab.happybeer.core.BaseViewHolder
import com.fab.happybeer.data.model.Favorite
import com.fab.happybeer.databinding.FavoriteItemBinding
import com.fab.happybeer.ui.loadImage

class FavoriteListAdapter(
    private val favoriteList: List<Favorite>,
    private val itemClickListener: OnRatingClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnRatingClickListener {
        fun onRatingClickListener(favoriteId: Int, rating: Float)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = FavoriteListViewHolder(itemBinding)

        itemBinding.ratingBeer.setOnRatingBarChangeListener { ratingBar, rating, b ->
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnRatingBarChangeListener
            itemClickListener.onRatingClickListener(favoriteList[position].id, rating)
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is FavoriteListViewHolder -> holder.bind(favoriteList[position])
        }
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    private inner class FavoriteListViewHolder(
        val binding: FavoriteItemBinding
    ) : BaseViewHolder<Favorite>(binding.root) {
        override fun bind(item: Favorite) {
            binding.tvName.text = item.name
            binding.tvContributed.text = item.tagline
            item.image_url?.let { binding.imgBeer.loadImage(it) }
            binding.ratingBeer.rating = item.rating
        }
    }
}