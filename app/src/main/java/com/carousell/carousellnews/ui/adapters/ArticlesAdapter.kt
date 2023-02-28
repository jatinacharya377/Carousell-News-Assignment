package com.carousell.carousellnews.ui.adapters

import com.carousell.carousellnews.data.model.Article
import com.carousell.carousellnews.databinding.LayoutArticleItemBinding

class ArticlesAdapter: RecyclerAdapterBase<Article, LayoutArticleItemBinding>(LayoutArticleItemBinding::inflate) {

    override fun onBind(binding: LayoutArticleItemBinding, item: Article, position: Int) {
        binding.article = item
    }
}