package com.utad.pmdmu5.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.utad.pmdmu5.data.network.model.GamesModelItem
import com.utad.pmdmu5.databinding.ItemBinding
import com.utad.pmdmu5.utils.loadFromUrl

class Adapter() : ListAdapter<GamesModelItem, Adapter.BaseViewHolder>(ItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(inflater, parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.tvItemTitle.text = item.title
        holder.binding.tvItemPrice1.text = item.normalPrice
        holder.binding.tvItemPrice2.text = item.salePrice
        holder.binding.ivItemImage.loadFromUrl(item.thumb, holder.binding.root.context)

    }

    inner class BaseViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)
}

object ItemCallBack : DiffUtil.ItemCallback<GamesModelItem>() {
    override fun areItemsTheSame(oldItem: GamesModelItem, newItem: GamesModelItem): Boolean {
        return oldItem.gameID == newItem.gameID
    }
    override fun areContentsTheSame(oldItem: GamesModelItem, newItem: GamesModelItem): Boolean {
        return oldItem == newItem
    }
}