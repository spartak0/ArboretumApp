package com.example.arboretum.domain.mapper

import android.graphics.Bitmap
import android.graphics.Color
import com.example.arboretum.utils.Constant
import java.nio.ByteBuffer
import java.nio.ByteOrder


class BitmapMapper {
    fun bitmapToByteBuffer(image: Bitmap): ByteBuffer {
        val bitmap = Bitmap.createScaledBitmap(
            image,
            Constant.TFLITE_IMAGE_WIDTH,
            Constant.TFLITE_IMAGE_HEIGHT,
            true
        )
        val input =
            ByteBuffer.allocateDirect(Constant.TFLITE_IMAGE_WIDTH * Constant.TFLITE_IMAGE_HEIGHT * 3 * 4)
                .order(ByteOrder.nativeOrder())
        for (y in 0 until Constant.TFLITE_IMAGE_HEIGHT) {
            for (x in 0 until Constant.TFLITE_IMAGE_WIDTH) {
                val px = bitmap.getPixel(x, y)

                val r: Int = Color.red(px)
                val g: Int = Color.green(px)
                val b: Int = Color.blue(px)

                val rf = r / 255.0f
                val gf = g / 255.0f
                val bf = b / 255.0f

                input.putFloat(rf)
                input.putFloat(gf)
                input.putFloat(bf)
            }
        }
        return input
    }
}