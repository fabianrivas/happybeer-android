package com.fab.happybeer.data.model

data class Beer(
    val id: Int = -1,
    val abv: Double = -1.0,
    val brewers_tips: String = "",
    val first_brewed: String = "",
    val food_pairing: List<String>? = null,
    val ibu: Double = -1.0,
    val image_url: String? = "",
    val ingredients: Ingredients? = null,
    val name: String = "",
    val tagline: String = "",
    val target_fg: Int = -1,
    val target_og: Double = -1.0,
    val volume: Volume? = null,
    val rating: Float = -1.0f
)

data class Ingredients(
    val hops: List<Hop>? = null,
    val malt: List<Malt>? = null,
    val yeast: String = ""
)

data class Volume(
    val unit: String = "",
    val value: Int = -1
)

data class Hop(
    val add: String = "",
    val amount: Amount? = null,
    val attribute: String = "",
    val name: String = ""
)

data class Malt(
    val amount: Amount? = null,
    val name: String = ""
)

data class Amount(
    val unit: String = "",
    val value: Double = -1.0
)