package com.example.thirdeye.ui.intruders

import android.app.Application
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.thirdeye.data.encryptedStorage.EncryptedStorageRepository
import com.example.thirdeye.data.models.IntrudersImages
import com.example.thirdeye.helper.LockImagePrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class IntruderPhotosViewModel(application: Application): AndroidViewModel(application) {

    val repo= EncryptedStorageRepository(application.applicationContext)
    val lockedPref= LockImagePrefs(application.applicationContext)
    private val _images= MutableStateFlow<List<IntrudersImages>>(emptyList())
    val images=_images.asStateFlow()

    fun loadImages() {
        viewModelScope.launch(Dispatchers.IO) {
            val files = repo.listOfImages()
                .sortedByDescending { it.lastModified() }

            if (files.size>3){
                val fourthImage=files[0]
                if (!lockedPref.isLocked(fourthImage.name)){

                    lockedPref.addLocked(fourthImage.name)
                }
            }

            val imageList = files.map { file ->
                val bytes = repo.readEncryptedImage(file)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)


                val isLocked = lockedPref.isLocked(file.name)


                IntrudersImages(
                    id = file.name,
                    bitmap = bitmap,
                    isLocked = isLocked,
                    file = file
                )
            }

            _images.value = imageList
        }
    }


    fun deleteImage(file: File) {
        viewModelScope.launch(Dispatchers.IO) {

                repo.deleteImage(file)
            if (lockedPref.isLocked(file.name)) {
                lockedPref.removeLocked(file.name)
                loadImages()
            }

    }
    }
    fun unlockImage(id:String){
        lockedPref.removeLocked(id)
        loadImages()
    }
}