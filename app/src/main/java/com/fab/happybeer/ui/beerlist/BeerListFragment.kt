package com.fab.happybeer.ui.beerlist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fab.happybeer.R
import com.fab.happybeer.core.Result
import com.fab.happybeer.data.local.AppDatabase
import com.fab.happybeer.data.local.favorite.LocalFavoriteDataSource
import com.fab.happybeer.data.model.Beer
import com.fab.happybeer.data.remote.RemoteBeerDataSource
import com.fab.happybeer.data.remote.RetrofitClient
import com.fab.happybeer.databinding.FragmentBeerListBinding
import com.fab.happybeer.presentation.beer.BeerViewModel
import com.fab.happybeer.presentation.beer.BeerViewModelFactory
import com.fab.happybeer.presentation.favorite.FavoriteViewModel
import com.fab.happybeer.presentation.favorite.FavoriteViewModelFactory
import com.fab.happybeer.repository.beer.BeerRepositoryImpl
import com.fab.happybeer.repository.favorite.FavoriteRepositoryImpl
import com.fab.happybeer.ui.beerlist.adapter.BeerListAdapter
import com.fab.happybeer.ui.beerlist.adapter.BeerLoadStateAdapter
import com.fab.happybeer.ui.toJson

class BeerListFragment : Fragment(R.layout.fragment_beer_list), BeerListAdapter.OnBeerClikListener {

    private lateinit var binding: FragmentBeerListBinding
    private val adapter = BeerListAdapter(this)

    private val beerViewModel by viewModels<BeerViewModel> {
        BeerViewModelFactory(
            BeerRepositoryImpl(
                RemoteBeerDataSource(RetrofitClient.webService)
            )
        )
    }
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory(
            FavoriteRepositoryImpl(
                LocalFavoriteDataSource(
                    AppDatabase.getDatabase(requireContext()).favoriteDao()
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBeerListBinding.bind(view)
        binding.rvBeerList.adapter =
            adapter.withLoadStateFooter(BeerLoadStateAdapter { adapter.retry() })

        beerViewModel.fetchBeerList().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitData(lifecycle, it)
            }
        })
    }

    override fun onBeerListClick(beer: Beer) {
        findNavController().navigate(
            BeerListFragmentDirections.actionBeerListFragmentToBeerDetailFragment(
                beer.toJson()
            )
        )
    }

    override fun onFavoriteClick(beer: Beer) {
        favoriteViewModel.saveFavorite(beer).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    showMessageAddFavorite(getString(R.string.msg_add_favorite_success))
                }
                is Result.Failure -> {
                    showMessageAddFavorite(getString(R.string.msg_add_favorite_error))
                }
            }
        })
    }

    private fun showMessageAddFavorite(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}