package com.jayjeet.realtimefacedetector2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.app.Activity
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val READ_REQUEST_CODE: Int = 42
    private var guri: Uri = Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn: Button = findViewById(R.id.open_button)
        val detectBtn: Button = findViewById(R.id.detect_button)
        btn.setOnClickListener(View.OnClickListener {
            performFileSearch()
        });
        detectBtn.setOnClickListener(View.OnClickListener {
            detectFaces(guri);
        })
    }

    fun detectFaces(uri: Uri) {
        val detector = RealtimeFaceDetector()
        val image = detector.loadImageFromUri(applicationContext, uri)
        val results: TextView = findViewById(R.id.results_view)
        detector.detectImage(image, results);
    }

    fun performFileSearch() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    fun showImage() {
        val imageView: ImageView = findViewById(R.id.image_display)
        imageView.setImageURI(guri);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                guri = uri
                showImage()
            }
        }
    }
}

