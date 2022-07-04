package com.fab.happybeer.ui

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.fab.happybeer.data.model.Beer
import com.google.gson.Gson
import com.squareup.picasso.Picasso

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun ImageView.loadImage(url: String) {
    Picasso.get().load(url).into(this);
}

fun String.toBase64(): String {
    return String(
        android.util.Base64.encode(this.toByteArray(), android.util.Base64.DEFAULT)
    )
}

fun String.fromBase64(): String {
    return String(
        android.util.Base64.decode(this, android.util.Base64.DEFAULT)
    )
}

fun Beer.toJson(): String {
    return Gson().toJson(this)
}

fun String.toData(): Beer {
    return Gson().fromJson(this, Beer::class.java)
}