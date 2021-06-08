package com.wp.ayenosis.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.wp.ayenosis.Adapter.DetectionAdapter
import com.wp.ayenosis.Adapter.HistoryAdapter
import com.wp.ayenosis.Adapter.QuestionAnswerAdapter
import com.wp.ayenosis.R
import com.wp.ayenosis.model.Detection
import com.wp.ayenosis.model.QuestionAnswer
import com.wp.ayenosis.utils.FirebaseUtils
import com.wp.ayenosis.utils.FirebaseUtils.firebaseUser

class HistoryActivity : AppCompatActivity() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var collectionRecyclerView: CollectionReference
    private lateinit var rvHistory: RecyclerView
    private var arrayListDetection: MutableList<Detection> = arrayListOf()
    private lateinit var uid: String

    var detectionAdapter: DetectionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.hide()

        val user = FirebaseUtils.firebaseAuth.currentUser
        if (user != null) {
            uid = user.uid
        }
        collectionRecyclerView = db.collection("userData").document(uid).collection("detection")

        val tvTitle: TextView = findViewById(R.id.tv_title)
        tvTitle.text = "Saved History"
        rvHistory = findViewById(R.id.rv_history)
        showRecyclerList()


        val btnBack: ImageView = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val menuNav: BottomNavigationView = findViewById(R.id.menu_navigation_history)
        menuNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.btn_history -> {
                    Toast.makeText(this, "You are already in the History", Toast.LENGTH_SHORT).show()
                }
                R.id.btn_question -> {
                    val mIntent = Intent(this@HistoryActivity, FaqActivity::class.java)
                    startActivity(mIntent)
                    finish()
                }
                R.id.btn_home -> {
                    val mIntent = Intent(this@HistoryActivity, MainActivity::class.java)
                    startActivity(mIntent)
                    finish()
                }
            }
            true}

    }


    private fun showRecyclerList() {
       // rvHistory.layoutManager = LinearLayoutManager(this)
        //val historyAdapter = HistoryAdapter(arrayListDetection)
        //rvHistory.adapter = historyAdapter

        val query: Query = collectionRecyclerView
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<Detection> = FirestoreRecyclerOptions.Builder<Detection>()
            .setQuery(query, Detection::class.java)
            .build()

        detectionAdapter = DetectionAdapter(firestoreRecyclerOptions)
        rvHistory.layoutManager = LinearLayoutManager(this)
        rvHistory.adapter = detectionAdapter
    }

    override fun onStart() {
        super.onStart()
        detectionAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        detectionAdapter!!.stopListening()
    }
}