package com.fab.happybeer.ui.favoritelist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.fab.happybeer.R
import com.fab.happybeer.core.Result
import com.fab.happybeer.data.local.AppDatabase
import com.fab.happybeer.data.local.favorite.LocalFavoriteDataSource
import com.fab.happybeer.databinding.FragmentFavoriteListBinding
import com.fab.happybeer.presentation.favorite.FavoriteViewModel
import com.fab.happybeer.presentation.favorite.FavoriteViewModelFactory
import com.fab.happybeer.repository.favorite.FavoriteRepositoryImpl
import com.fab.happybeer.ui.favoritelist.adapter.FavoriteListAdapter
import com.kennyc.view.MultiStateView


class FavoriteListFragment : Fragment(R.layout.fragment_favorite_list),
    FavoriteListAdapter.OnRatingClickListener {

    private lateinit var binding: FragmentFavoriteListBinding

    private val viewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory(
            FavoriteRepositoryImpl(
                LocalFavoriteDataSource(
                    AppDatabase.getDatabase(
                        requireContext()
                    ).favoriteDao()
                )
            )
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteListBinding.bind(view)

        viewModel.fetchFavoriteList().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.multiStateView.viewState = MultiStateView.ViewState.LOADING
                }
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        binding.multiStateView.viewState = MultiStateView.ViewState.EMPTY
                        return@Observer
                    }
                    binding.rvFavoriteList.adapter = FavoriteListAdapter(result.data, this)
                    binding.multiStateView.viewState = MultiStateView.ViewState.CONTENT
                }
                is Result.Failure -> {
                    binding.multiStateView.viewState = MultiStateView.ViewState.ERROR
                }
            }
        })
    }

    override fun onRatingClickListener(favoriteId: Int, rating: Float) {
        viewModel.saveRating(favoriteId, rating).observe(viewLifecycleOwner, Observer {})
    }
}