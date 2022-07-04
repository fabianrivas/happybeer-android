package com.fab.happybeer.ui.beerdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.fab.happybeer.R
import com.fab.happybeer.data.model.Beer
import com.fab.happybeer.databinding.FragmentBeerDetailBinding
import com.fab.happybeer.ui.loadImage
import com.fab.happybeer.ui.toData


class BeerDetailFragment : DialogFragment(R.layout.fragment_beer_detail) {

    private lateinit var binding: FragmentBeerDetailBinding
    private val args by navArgs<BeerDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            R.style.FullScreenDialogStyle
        );
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBeerDetailBinding.bind(view)
        args.let {
            setupView(it.beer.toData())
        }
    }

    private fun setupView(beer: Beer) {
        if (!beer.image_url.isNullOrEmpty())
            binding.imgBeer.loadImage(beer.image_url)
        binding.tvName.text = beer.name
        binding.tvYeast.text = beer.ingredients?.yeast
        binding.tvVolume.text =
            getString(R.string.msg_volume, beer.volume?.value.toString(), beer.volume?.unit)
        binding.tvAbv.text = getString(R.string.msg_abv, beer.abv.toString())
        binding.tvIbu.text = beer.ibu.toString()
        binding.tvOg.text = beer.target_og.toString()
        binding.tvFg.text = beer.target_fg.toString()
        binding.tvTagline.text = beer.tagline
        binding.tvFirstBrewed.text = getString(R.string.msg_first_brewed, beer.first_brewed)
        binding.tvBrewersTips.text = beer.brewers_tips
        val foodPairing = StringBuilder()
        beer.food_pairing?.forEach {
            foodPairing.append("- ${it}\n")
        }
        binding.tvFoodPairing.text = foodPairing

        val hops = StringBuilder()
        beer.ingredients?.hops?.forEach {
            hops.append("- ${it.name} ${it.amount?.value} ${it.amount?.unit}\n")
        }
        binding.tvHops.text = hops

        val malts = StringBuilder()
        beer.ingredients?.malt?.forEach {
            malts.append("- ${it.name} ${it.amount?.value} ${it.amount?.unit}\n")
        }
        binding.tvMalt.text = malts
    }

}