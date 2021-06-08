package com.wp.ayenosis.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.wp.ayenosis.R
import com.wp.ayenosis.model.Detection

class DetectionAdapter(options: FirestoreRecyclerOptions<Detection>) :
    FirestoreRecyclerAdapter<Detection, DetectionAdapter.DetectionAdapterView>(options) {


    class DetectionAdapterView(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvNormalHistory: TextView = itemView.findViewById(R.id.tv_normal_history)
        val tvCataractHistory: TextView = itemView.findViewById(R.id.tv_cataract_history)
        val tvDateHistory: TextView = itemView.findViewById(R.id.tv_date_history)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetectionAdapterView {
        return DetectionAdapterView(LayoutInflater.from(parent.context).inflate(
            R.layout.list_history_item,
            parent,
            false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DetectionAdapterView, position: Int, model: Detection) {
        val cataractPercent =  String.format("%.3f", model.cataractPercent!! *100)
        val normalPercent = String.format("%.3f", model.normalPercent!! *100)
        holder.tvNormalHistory.text = "Normal   : $normalPercent%"
        holder.tvCataractHistory.text = "Cataract : $cataractPercent%"
    }

}