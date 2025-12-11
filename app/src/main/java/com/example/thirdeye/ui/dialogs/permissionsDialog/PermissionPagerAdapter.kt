package com.example.thirdeye.ui.dialogs.permissionsDialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thirdeye.R
import com.example.thirdeye.databinding.PermissionsViewpagerItemBinding

class PermissionPagerAdapter(
    private val permissions: List<String>
) : RecyclerView.Adapter<PermissionPagerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PermissionsViewpagerItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding= PermissionsViewpagerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val permission = permissions[position]
        holder.binding.permissionTitle.text = readableName(permission)
        holder.binding.permissionDescription.text = readableDescription(permission)


        holder.binding.permissionIcon.setImageResource(iconFor(permission))
    }

    override fun getItemCount(): Int = permissions.size

    private fun readableName(p: String) = when (p) {
        android.Manifest.permission.POST_NOTIFICATIONS -> "Notification Permission Needed"
        android.Manifest.permission.CAMERA -> "Camera Access Needed"
        android.Manifest.permission.READ_MEDIA_IMAGES -> "Read Images Permission Needed"
        android.Manifest.permission.READ_EXTERNAL_STORAGE -> "Storage Read Permission Needed"
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE -> "Storage Write Permission Needed"
        else -> "Permission Required"
    }

    private fun readableDescription(p: String) = when (p) {
        android.Manifest.permission.POST_NOTIFICATIONS -> "We need to send you notifications."
        android.Manifest.permission.CAMERA -> "We need camera access to take photos."
        android.Manifest.permission.READ_MEDIA_IMAGES -> "We need access to your images."
        android.Manifest.permission.READ_EXTERNAL_STORAGE -> "We need access to your storage to read files."
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE -> "We need storage write permission to save files."
        else -> "Permission is required to continue."
    }

    private fun iconFor(p: String) = when (p) {
        android.Manifest.permission.POST_NOTIFICATIONS -> R.drawable.notificationicon
        android.Manifest.permission.CAMERA -> R.drawable.solar_camera_bold
        android.Manifest.permission.READ_MEDIA_IMAGES -> R.drawable.overlayicon
        android.Manifest.permission.READ_EXTERNAL_STORAGE -> R.drawable.administratoricon
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE -> R.drawable.solar_camera_bold
        else -> R.drawable.ic_launcher_background
    }

}
