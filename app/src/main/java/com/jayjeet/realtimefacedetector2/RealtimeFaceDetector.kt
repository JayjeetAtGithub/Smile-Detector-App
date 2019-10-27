package com.jayjeet.realtimefacedetector2

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.TextView
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.*;

class RealtimeFaceDetector() {

    val options = FirebaseVisionFaceDetectorOptions.Builder()
        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
        .build()

    fun loadImageFromUri(ctx: Context, uri: Uri): FirebaseVisionImage {
        return  FirebaseVisionImage.fromFilePath(ctx, uri)
    }

    fun detectImage(image : FirebaseVisionImage, textView: TextView) {
        val detector = FirebaseVision.getInstance().getVisionFaceDetector(options);
        detector.detectInImage(image)
            .addOnSuccessListener { faces ->
                Log.d("INFO:", "Success")
                if (faces[0].smilingProbability * 100 > 90 ){
                    textView.setText("You look quite happy !");
                }
                else {
                    textView.setText("You look a bit sad !")
                }
            }
            .addOnFailureListener { error ->
                Log.d("INFO:", "Failure");
                textView.setText("No Face Detected")
            }
    }
}
