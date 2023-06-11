package com.example.arboretum.data.ml.service

import android.content.Context
import android.util.Log
import com.example.arboretum.data.ml.model.ModelPlant
import com.example.arboretum.ml.Model2
import com.example.arboretum.utils.Constant
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.InputStream
import java.nio.ByteBuffer

class ModelService(private val context: Context) {
    fun predict(byteBuffer: ByteBuffer): ModelPlant {
        val model = Model2.newInstance(context)
        val inputFeature0 = TensorBuffer.createFixedSize(
            intArrayOf(
                1,
                Constant.TFLITE_IMAGE_WIDTH,
                Constant.TFLITE_IMAGE_HEIGHT,
                3
            ), DataType.FLOAT32
        )
        inputFeature0.loadBuffer(byteBuffer)
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        model.close()
        val outputArray = outputFeature0.floatArray
        val maxIndex = outputArray.indices.maxBy { outputArray[it] }
        val categories =
            readCsv(context.assets.open("cat_info.csv"))
        Log.d("AAA", "predict: $maxIndex")
        Log.d("AAA", "predict: ${categories[maxIndex]}")
        return categories[maxIndex]
    }

    private fun readCsv(inputStream: InputStream): List<ModelPlant> {
        val reader = inputStream.bufferedReader()
        val header = reader.readLine()
        return reader.lineSequence()
            .map {
                val (name, genus, phylum, classPlant, classId) = it.split(',', ignoreCase = false, limit = 5)
                ModelPlant(name, genus, phylum, classPlant)
            }.toList()
    }
}