package com.wp.ayenosis.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wp.ayenosis.R
import com.wp.ayenosis.model.Detection

class HistoryAdapter (private val detectionItem: MutableList<Detection>) : RecyclerView.Adapter<HistoryAdapter.ItemHolder>() {
    class ItemHolder (v: View) : RecyclerView.ViewHolder(v) {
        val tvNormalHistory: TextView = itemView.findViewById(R.id.tv_normal_history)
        val tvCataractHistory: TextView = itemView.findViewById(R.id.tv_cataract_history)
        val tvDateHistory: TextView = itemView.findViewById(R.id.tv_date_history)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.ItemHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.list_history_item,
            parent,
            false
        )
        return ItemHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryAdapter.ItemHolder, position: Int) {
        val detection = detectionItem[position]
        val normalPercent = detection.normalPercent?.times(100)
        val cataractPercent = detection.cataractPercent?.times(100)
        holder.tvNormalHistory.text = "Normal $normalPercent%"
        holder.tvCataractHistory.text = "Normal $cataractPercent%"
        holder.tvDateHistory.text = detection.timeDate
    }

    override fun getItemCount() = detectionItem.size


}