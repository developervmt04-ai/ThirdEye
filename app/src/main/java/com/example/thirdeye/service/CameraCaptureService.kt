package com.example.thirdeye.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.ImageFormat
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.media.ImageReader
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.util.Log
import androidx.collection.intSetOf
import androidx.core.content.ContextCompat
import com.example.thirdeye.notifications.Notifications

class CameraCaptureService : Service() {

    companion object {
        private const val NOTIF_ID = 1001
        var Instance: CameraCaptureService? = null
        private const val TAG = "Camera2Service"


        fun start(context: Context) {
            val intent = Intent(context, CameraCaptureService::class.java)
            ContextCompat.startForegroundService(context, intent)


        }
    }

    private var cameraDevice: CameraDevice? = null
    private var captureSession: CameraCaptureSession? = null
    private var imageReader: ImageReader? = null
    private lateinit var bgThread: HandlerThread
    private lateinit var bgHandler: Handler
    private var appPendingIntent: PendingIntent? = null

    override fun onCreate() {
        super.onCreate()
        Instance = this
        Notifications.createChannels(this)
        startForeground(NOTIF_ID, Notifications.persistentNotification(this))



        bgThread = HandlerThread("Camera2Background")
        bgThread.start()
        bgHandler = Handler(bgThread.looper)

        imageReader = ImageReader.newInstance(640, 480, ImageFormat.JPEG, 2)
        imageReader!!.setOnImageAvailableListener({ reader ->
            val images = reader.acquireLatestImage() ?: return@setOnImageAvailableListener
            images.let {
                saveImage()
                it.close()
            }
        }, bgHandler)
        openCamera()


    }

    private fun openCamera() {
        val manager = getSystemService(CAMERA_SERVICE) as CameraManager
        val frontCameraId = manager.cameraIdList.first {
            manager.getCameraCharacteristics(it)
                .get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT
        }
        manager.openCamera(frontCameraId, object : CameraDevice.StateCallback() {
            override fun onDisconnected(camera: CameraDevice) {
                camera.close()

            }

            override fun onError(camera: CameraDevice, error: Int) {
                camera.close()
                camera.close()
            }

            override fun onOpened(camera: CameraDevice) {
                cameraDevice = camera
                createSession()

            }

        }, bgHandler)

    }

    private fun createSession() {
        cameraDevice ?: return
        imageReader!!.surface
        cameraDevice?.createCaptureSession(
            listOf(imageReader!!.surface),
            object : CameraCaptureSession.StateCallback() {
                override fun onConfigureFailed(session: CameraCaptureSession) {
                    Log.e(TAG, "Capture session failed")
                }

                override fun onConfigured(session: CameraCaptureSession) {
                    captureSession = session
                    Log.d(TAG, "CaptureSession READY ")
                }


            }, bgHandler
        )
    }

    fun saveImage() {
        getSystemService(NotificationManager::class.java).notify(
            101,
            Notifications.intrusionNotification(this)
        )


    }

    fun captureIntruderPhoto() {
        cameraDevice ?: return
        captureSession ?: return
        imageReader!!.surface

        val request =
            cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE).apply {
                this!!.addTarget(imageReader!!.surface)
                set(
                    CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON)
                    set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF)


                }
            }
        if (request != null) {
            captureSession?.capture(request.build(), null, bgHandler)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        captureSession?.close()
        cameraDevice?.close()
        imageReader?.close()
        bgThread.quitSafely()
        Instance = null

    }


    override fun onBind(intent: Intent?) = null
}