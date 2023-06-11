package com.example.arboretum.ui.main_screen

import com.example.arboretum.R

sealed class BottomNavigationItem(val iconId: Int) {
    object CameraItem : BottomNavigationItem(iconId = R.drawable.baseline_photo_camera_100)
    object UploadItem : BottomNavigationItem(iconId = R.drawable.baseline_drive_folder_upload_100)
}