package com.carousell.carousellnews.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.carousell.carousellnews.R

object CustomBinder {
    @JvmStatic
    @BindingAdapter("loadImage")
    fun bindImageUrl(imageView: ImageView, url: String) {
        Glide.with(imageView.context).load(url).placeholder(R.drawable.ic_carousell_news_logo).error(R.drawable.ic_carousell_news_logo).into(imageView)
    }
}