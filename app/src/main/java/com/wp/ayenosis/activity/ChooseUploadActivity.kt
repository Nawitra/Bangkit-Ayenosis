@file:Suppress("DEPRECATION")

package com.wp.ayenosis.activity

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.wp.ayenosis.R
import com.wp.ayenosis.ml.ConvertedModel
import com.wp.ayenosis.model.Detection
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.time.LocalDateTime
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase
import com.wp.ayenosis.utils.FirebaseUtils.firebaseAuth

class ChooseUploadActivity : AppCompatActivity() {
    private lateinit var bitmap: Bitmap
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_upload)

        supportActionBar?.hide()


        val btnBack: ImageView = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnFromFile: Button = findViewById(R.id.btn_from_file)
        btnFromFile.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_GET_CONTENT)
            myIntent.type = "image/*"
            startActivityForResult(myIntent, 100)
        }

        val btnFromCamera: Button = findViewById(R.id.btn_from_camera)
        btnFromCamera.setOnClickListener{
            val myIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(myIntent, 110)
        }

        val btnSubmit: Button = findViewById(R.id.btn_submit1)
        btnSubmit.setOnClickListener{
            val normalP: Float = 0.17F
            val cataractP: Float = 0.83F
            val dateTime = LocalDateTime.now()

            val detection = Detection()
            detection.normalPercent = normalP
            detection.cataractPercent = cataractP
            detection.timeDate = dateTime

            val data = hashMapOf(
                "normal" to detection.normalPercent,
                "cataract" to detection.cataractPercent
            )

            val db = FirebaseFirestore.getInstance()

            val user = firebaseAuth.currentUser
            if (user != null) {
                uid = user.uid
            }



            db.collection("userData").document(uid).collection("detection")
                .add(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                }

            intentToResult(detection)

/*
        val bitmapScaled = Bitmap.createScaledBitmap(bitmap, 256, 192, true)
            val byteBuffer = ByteBuffer.allocateDirect(4 * 3 * 192 * 256)
            for (y in 0 until 192) {
                for (x in 0 until 256) {
                    val px = bitmapScaled.getPixel(x, y)

                    val r = Color.red(px)
                    val g = Color.green(px)
                    val b = Color.blue(px)

                    val rf = (r) / 255f
                    val gf = (g) / 255f
                    val bf = (b) / 255f

                    byteBuffer.putFloat(rf)
                    byteBuffer.putFloat(gf)
                    byteBuffer.putFloat(bf)
                }
            }

            val tensorImage = TensorImage(DataType.FLOAT32)
            TensorImage.fromBitmap(bitmapScaled)
            val model = ConvertedModel.newInstance(this)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 192, 256, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val tvPrediction: TextView = findViewById(R.id.tv_prediction)
            tvPrediction.text = outputFeature0.floatArray[0].toString()

            model.close()
*/
        }

    }


    private fun intentToResult(data: Detection) {
        val intent = Intent(this, ShowResultActivity::class.java)
        intent.putExtra("DetectionKey", data)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val ivFile: ImageView = findViewById(R.id.iv_file)
        if(requestCode==100 && resultCode == Activity.RESULT_OK && data != null){
            val filepath: Uri = data.data!!
            val bitmap1: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
            bitmap = Bitmap.createScaledBitmap(bitmap1, 256, 192, true)
        }else if(requestCode==110 && resultCode == Activity.RESULT_OK){
            bitmap = data?.extras?.get("data") as Bitmap
        }
        ivFile.setImageBitmap(bitmap)
    }
}