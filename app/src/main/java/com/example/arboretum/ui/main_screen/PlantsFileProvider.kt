package com.example.arboretum.ui.main_screen

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.example.arboretum.R
import java.io.File

class PlantsFileProvider : FileProvider(R.xml.filepath){
    companion object {
        fun getImageUri(context: Context): Uri {
            val directory = File(context.filesDir, "images")
            directory.mkdirs()
            val file = File.createTempFile(
                "image_",
                ".jpg",
                directory
            )
            val authority = context.packageName + ".file-provider"
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}