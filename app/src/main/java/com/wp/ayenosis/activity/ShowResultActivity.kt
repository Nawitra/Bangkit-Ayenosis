package com.wp.ayenosis.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.wp.ayenosis.R
import com.wp.ayenosis.model.Detection

class ShowResultActivity : AppCompatActivity() {
    private val detectionKey = "DetectionKey"
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_result)

        supportActionBar?.hide()

        val det = intent.getParcelableExtra<Detection>("DetectionKey") as Detection

        val tvNormal: TextView = findViewById(R.id.tv_normal)
        val tvCataract: TextView = findViewById(R.id.tv_cataract)

        val cataractPercent =  String.format("%.3f", det.cataractPercent!! *100)
        val normalPercent = String.format("%.3f", det.normalPercent!! *100)
        tvNormal.text = "Normal  : $normalPercent%"
        tvCataract.text = "Cataract: $cataractPercent%"

        val tvDetect: TextView = findViewById(R.id.tv_detect)
        if(det.cataractPercent!! > 0.5){
            tvDetect.text = "We have detected that your eye might have cataract. As such, we highly encourage you to get a further diagnosis from the hospital."
        }else{
            tvDetect.text = "Based on what we have detected, your eye might not have cataract. Although, it is advisable to check up to your local doctor in case you feel that this is not the case."
        }

        val btnOkay: Button = findViewById(R.id.btn_okay)
        btnOkay.setOnClickListener{
            val mIntent = Intent(this@ShowResultActivity, MainActivity::class.java)
            startActivity(mIntent)
            finish()
        }

    }
}