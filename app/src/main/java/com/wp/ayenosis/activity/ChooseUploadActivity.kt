@file:Suppress("DEPRECATION")

package com.wp.ayenosis.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.wp.ayenosis.R
import com.wp.ayenosis.ml.Model1
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ChooseUploadActivity : AppCompatActivity() {
    private lateinit var bitmap: Bitmap
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

            val bitmapScaled = Bitmap.createScaledBitmap(bitmap, 192, 256, true)
            val byteBuffer = ByteBuffer.allocateDirect(192*256*3*4).order(ByteOrder.nativeOrder())
            for (y in 0 until 256) {
                for (x in 0 until 192) {
                    val px = bitmap.getPixel(x, y)

                    val r = Color.red(px)
                    val g = Color.green(px)
                    val b = Color.blue(px)

                    val rf = (r - 127) / 255f
                    val gf = (g - 127) / 255f
                    val bf = (b - 127) / 255f

                    byteBuffer.putFloat(rf)
                    byteBuffer.putFloat(gf)
                    byteBuffer.putFloat(bf)
                }
            }

            TensorImage.fromBitmap(bitmapScaled)
            val model = Model1.newInstance(this)

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 192, 256, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val tvPrediction: TextView = findViewById(R.id.tv_prediction)
            tvPrediction.text = "hi"

            model.close()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val ivFile: ImageView = findViewById(R.id.iv_file)
        if(requestCode==100 && resultCode == Activity.RESULT_OK && data != null){
            var filepath: Uri = data.data!!
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
        }else if(requestCode==110 && resultCode == Activity.RESULT_OK){
            bitmap = data?.extras?.get("data") as Bitmap
        }
        ivFile.setImageBitmap(bitmap)
    }
}