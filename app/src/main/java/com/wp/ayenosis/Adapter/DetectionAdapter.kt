package com.wp.ayenosis.Adapter

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

    override fun onBindViewHolder(holder: DetectionAdapterView, position: Int, model: Detection) {
        //val normalPercent = model.normalPercent?.times(100)
        //val cataractPercent = model.cataractPercent?.times(100)
        holder.tvNormalHistory.text = model.normalPercent.toString()
        holder.tvCataractHistory.text = model.cataractPercent.toString()
    }

}