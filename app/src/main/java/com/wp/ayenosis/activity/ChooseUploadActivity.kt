@file:Suppress("DEPRECATION")

package com.wp.ayenosis.activity

import android.annotation.SuppressLint
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
import com.google.firebase.firestore.*
import com.wp.ayenosis.R
import com.wp.ayenosis.ml.ConvertedModel1
import com.wp.ayenosis.ml.ConvertedModelSemogauint
import com.wp.ayenosis.model.Detection
import java.time.LocalDateTime
import com.wp.ayenosis.utils.FirebaseUtils.firebaseAuth
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ChooseUploadActivity : AppCompatActivity() {
    private lateinit var bitmap: Bitmap
    private lateinit var uid: String
    private var arrayListDetection: MutableList<Detection> = arrayListOf()

    @SuppressLint("SimpleDateFormat")
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

            val data = hashMapOf(
                "normal" to detection.normalPercent,
                "cataract" to detection.cataractPercent,
                "date" to detection.timeDate
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

            //intentToResult(detection)

 /*           db.collection("userData").document(uid).collection("detection")
                .addSnapshotListener(object: EventListener<QuerySnapshot>{
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        for(dc: DocumentChange in value?.documentChanges!!){
                            if(dc.type == DocumentChange.Type.ADDED){
                                arrayListDetection.add(dc.document.toObject(Detection::class.java))
                            }
                        }
                    }
               })*/

        val bitmapScaled = Bitmap.createScaledBitmap(bitmap, 256, 192, true)
            val byteBuffer = ByteBuffer.allocateDirect(3*4*192*256)
            byteBuffer.order(ByteOrder.nativeOrder())
            val pixels = IntArray(256 * 192)
            bitmapScaled.getPixels(pixels, 0, bitmapScaled.width, 0, 0, bitmapScaled.width, bitmapScaled.height)
            var pixel = 0

            for (i in 0 until 256) {
                for (j in 0 until 192) {
                    val pixelVal = pixels[pixel++]

                    byteBuffer.putFloat(((pixelVal shr 16 and 0xFF)) / 255f)
                    byteBuffer.putFloat(((pixelVal shr 8 and 0xFF) ) / 255f)
                    byteBuffer.putFloat(((pixelVal and 0xFF) ) / 255f)

                }
            }
            bitmap.recycle()

            /*
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

                   // byteBuffer.putInt(px)
                }
            }
            */



            //val tensorImage = TensorImage(DataType.UINT8)
            //TensorImage.fromBitmap(bitmapScaled)
            val model = ConvertedModel1.newInstance(this)
            //var buffer1 = TensorImage.fromBitmap(bitmapScaled)
            //byteBuffer = buffer1.buffer
            //val image1 = TensorImage.fromBitmap(bitmap)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 192, 256, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            val tvPrediction: TextView = findViewById(R.id.tv_prediction)
            val tvPrediction1: TextView = findViewById(R.id.tv_prediction1)
            val tvPrediction2: TextView = findViewById(R.id.tv_prediction2)
            tvPrediction.text = outputFeature0.floatArray.toString()
            tvPrediction1.text = outputFeature0.floatArray[0].toString()
            tvPrediction2.text = outputFeature0.floatArray[1].toString()


            model.close()

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