package com.example.whiskerbuds.ml

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

object TFLiteHelper {

    fun uriToBitmap(context: Context, uri: Uri): Bitmap {
        val inputStream = context.contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream!!)
    }

    fun runModel(context: Context, bitmap: Bitmap): Triple<String, Float, String> {
        val labels = context.assets.open("labels.txt").bufferedReader().readLines()
        val model = Interpreter(loadModelFile(context, "mobilenet_v1_1.0_224.tflite"))
        val input = preprocess(bitmap)
        val output = Array(1) { FloatArray(labels.size) }
        model.run(input, output)

        val topIdx = output[0].indices.maxByOrNull { output[0][it] } ?: 0
        val label = labels[topIdx]
        val confidence = output[0][topIdx] * 100

        // Keywords to detect general pet type
        val dogKeywords = listOf(
            "retriever", "terrier", "spaniel", "bulldog", "shepherd", "poodle",
            "pug", "husky", "chihuahua", "boxer", "mastiff", "shih-tzu", "pinscher",
            "greyhound", "ridgeback", "wolfhound", "beagle", "rottweiler", "doberman"
        )

        val catKeywords = listOf(
            "cat", "siamese", "persian", "tabby", "egyptian", "tiger cat", "kitten"
        )

        val rabbitKeywords = listOf(
            "angora", "hare", "rabbit"
        )

        val labelLower = label.lowercase()

        val type = when {
            dogKeywords.any { labelLower.contains(it) } -> "Dog"
            catKeywords.any { labelLower.contains(it) } -> "Cat"
            rabbitKeywords.any { labelLower.contains(it) } -> "Rabbit"
            else -> "Other"
        }


        return Triple(label, confidence, type)
    }


    private fun loadModelFile(context: Context, filename: String): ByteBuffer {
        val fileDescriptor = context.assets.openFd(filename)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        return fileChannel.map(
            FileChannel.MapMode.READ_ONLY,
            fileDescriptor.startOffset,
            fileDescriptor.declaredLength
        )
    }

    private fun preprocess(bitmap: Bitmap): ByteBuffer {
        val resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val buffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3)
        buffer.order(ByteOrder.nativeOrder())

        for (y in 0 until 224) {
            for (x in 0 until 224) {
                val pixel = resized.getPixel(x, y)
                buffer.putFloat(((pixel shr 16 and 0xFF) / 255f))
                buffer.putFloat(((pixel shr 8 and 0xFF) / 255f))
                buffer.putFloat(((pixel and 0xFF) / 255f))
            }
        }
        return buffer
    }
}
