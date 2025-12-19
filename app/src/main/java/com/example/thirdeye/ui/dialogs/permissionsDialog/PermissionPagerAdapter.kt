package com.example.thirdeye.ui.dialogs.permissionsDialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.thirdeye.R
import com.example.thirdeye.databinding.PermissionsViewpagerItemBinding

class PermissionPagerAdapter(
    private val permissions: MutableList<String>
) : RecyclerView.Adapter<PermissionPagerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PermissionsViewpagerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PermissionsViewpagerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val permission = permissions[position]
        holder.binding.permissionTitle.text = readableName(permission)
        holder.binding.permissionDescription.text = readableDescription(permission)
        holder.binding.permissionIcon.setImageResource(iconFor(permission))
    }

    override fun getItemCount(): Int = permissions.size

    private fun readableName(permission: String) = when (permission) {
        android.Manifest.permission.POST_NOTIFICATIONS -> "Notification Permission Needed"
        android.Manifest.permission.CAMERA -> "Camera Access Needed"
        android.Manifest.permission.READ_MEDIA_IMAGES -> "Read Images Permission Needed"
        "DEVICE_ADMIN" -> "Administrator Access Needed"
        else -> "Permission Required"
    }

    private fun readableDescription(permission: String) = when (permission) {
        android.Manifest.permission.POST_NOTIFICATIONS -> "We need to send you notifications."
        android.Manifest.permission.CAMERA -> "We need camera access to take intruder photos."
        android.Manifest.permission.READ_MEDIA_IMAGES -> "We need storage access to save intruder images."
        "DEVICE_ADMIN" -> "Administrator access is required."
        else -> "Permission is required to continue."
    }

    private fun iconFor(permission: String) = when (permission) {
        android.Manifest.permission.POST_NOTIFICATIONS -> R.drawable.notificationicon
        android.Manifest.permission.CAMERA -> R.drawable.solar_camera_bold
        android.Manifest.permission.READ_MEDIA_IMAGES -> R.drawable.storageicon
        "DEVICE_ADMIN" -> R.drawable.administratoricon
        else -> R.drawable.ic_launcher_background
    }


    fun addAdminPage(pager: ViewPager2) {
        if (!permissions.contains("DEVICE_ADMIN")) {
            permissions.add("DEVICE_ADMIN")
            notifyItemInserted(permissions.lastIndex)
        }
    }
}
