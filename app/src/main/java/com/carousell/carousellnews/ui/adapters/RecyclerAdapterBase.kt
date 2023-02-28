package com.carousell.carousellnews.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.carousell.carousellnews.ui.fragments.Inflate

abstract class RecyclerAdapterBase<LT, VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : RecyclerView.Adapter<RecyclerAdapterBase<LT, VB>.ViewHolder>() {
    var list = ArrayList<LT>()
    val tempList = list

    abstract fun onBind(binding: VB, item: LT, position: Int)

    inner class ViewHolder(private val binding: VB) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(
            position: Int
        ) {
            val item = list[position]
            onBind(binding, item, position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<LT>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflate.invoke(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}