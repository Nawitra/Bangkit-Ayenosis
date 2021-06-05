package com.wp.ayenosis.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.wp.ayenosis.R
import com.wp.ayenosis.model.Detection

class ShowResultActivity : AppCompatActivity() {
    private val detectionKey = "DetectionKey"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_result)

        supportActionBar?.hide()

        var det = intent.getParcelableExtra<Detection>("DetectionKey") as Detection

        val tvNormal: TextView = findViewById(R.id.tv_normal)
        val tvCataract: TextView = findViewById(R.id.tv_cataract)

        tvNormal.text = det.normalPercent.toString()
        tvCataract.text = det.cataractPercent.toString()

        val btnOkay: Button = findViewById(R.id.btn_okay)

    }
}