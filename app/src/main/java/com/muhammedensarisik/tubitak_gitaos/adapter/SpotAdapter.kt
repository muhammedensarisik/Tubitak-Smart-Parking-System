package com.muhammedensarisik.tubitak_gitaos.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammedensarisik.tubitak_gitaos.databinding.ItemSpotBinding

class SpotAdapter(private val spotList: List<Pair<String, String>>) :
    RecyclerView.Adapter<SpotAdapter.SpotViewHolder>() {

    inner class SpotViewHolder(val binding: ItemSpotBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotViewHolder {
        val binding = ItemSpotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpotViewHolder, position: Int) {
        val spot = spotList[position]
        holder.binding.spotName.text = "${spot.first}: ${spot.second}"

        val bgColor = if (spot.second == "empty") Color.GREEN else Color.RED
        holder.binding.root.setBackgroundColor(bgColor)
    }

    override fun getItemCount() = spotList.size
}
