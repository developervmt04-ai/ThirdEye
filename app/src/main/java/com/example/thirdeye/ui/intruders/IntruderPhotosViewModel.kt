package com.example.thirdeye.ui.intruders

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.thirdeye.data.encryptedStorage.EncryptedStorageRepository
import com.example.thirdeye.data.models.IntrudersImages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class IntruderPhotosViewModel(application: Application): AndroidViewModel(application) {

    val repo= EncryptedStorageRepository(application.applicationContext)
    private val _images= MutableStateFlow<List<IntrudersImages>>(emptyList())
    val images=_images.asStateFlow()

    fun loadImages() {
        viewModelScope.launch(Dispatchers.IO) {
            val files: List<File> = repo.listOfImages()
            val imageList = files.mapIndexed { index, file ->
                val bytes = repo.readEncryptedImage(file)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                val isLocked = index >= 3
                IntrudersImages(file.name, bitmap, isLocked)
            }
            _images.value = imageList
        }
    }


    fun deleteImage(fileName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val file = repo.listOfImages().find { it.name == fileName }
            file?.let {
                repo.deleteImage(it)
                loadImages()
            }
        }
    }
}