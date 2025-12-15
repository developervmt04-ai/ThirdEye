package com.example.thirdeye.ui.intruders

import android.app.Application
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.thirdeye.data.encryptedStorage.EncryptedStorageRepository
import com.example.thirdeye.data.models.IntrudersImages
import com.example.thirdeye.data.localData.LockImagePrefs
import com.example.thirdeye.data.models.IntruderImageMetaData
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
@HiltViewModel
class IntruderPhotosViewModel @Inject constructor(
    application: Application,
    private val intruderRepo: IntruderRepo,
    private val lockedPref: LockImagePrefs
) : AndroidViewModel(application) {

    private val repo = EncryptedStorageRepository(application.applicationContext)

    private val _images = MutableStateFlow<List<IntrudersImages>>(emptyList())
    val images = _images.asStateFlow()

    fun loadImages() {
        viewModelScope.launch(Dispatchers.IO) {
            val files = repo.listOfImages().sortedByDescending { it.lastModified() }

            val imageList = files.map { file ->
                // 1️⃣ Get saved timestamp from Room metadata
                val metaFromDb = intruderRepo.getMeta(file.name)
                val savedTime = metaFromDb?.timeStamp ?: file.lastModified()

                // 2️⃣ Read encrypted image
                val (bytes, timeStamp) = repo.readEncryptedImage(file, savedTime)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                // 3️⃣ Check lock state
                val isLocked = lockedPref.isLocked(file.name)

                // 4️⃣ Save/Update metadata in Room
                val metadata = IntruderImageMetaData(id = file.name, timeStamp = timeStamp)
                intruderRepo.addImageData(metadata)


                IntrudersImages(
                    id = file.name,
                    bitmap = bitmap,
                    isLocked = isLocked,
                    file = file,
                    timeStamp = timeStamp
                )
            }

            _images.value = imageList
        }
    }

    fun deleteImage(file: File) {
        viewModelScope.launch(Dispatchers.IO) {
            // Delete encrypted image from storage
            repo.deleteImage(file)

            // Remove lock state
            if (lockedPref.isLocked(file.name)) {
                lockedPref.removeLocked(file.name)
            }


            intruderRepo.deleteData(file.name)


            loadImages()
        }
    }

    fun unlockImage(id: String) {
        viewModelScope.launch {
            // Remove lock state
            lockedPref.removeLocked(id)
            lockedPref.addManualUnlock(id)

            // Update UI instantly
            _images.update { list ->
                list.map { if (it.id == id) it.copy(isLocked = false) else it }
            }
        }
    }
}
