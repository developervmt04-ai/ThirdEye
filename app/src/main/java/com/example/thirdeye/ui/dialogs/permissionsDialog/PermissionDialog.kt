package com.example.thirdeye.ui.dialogs.permissionsDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.example.thirdeye.R
import com.example.thirdeye.databinding.PermissionsViewpagerBinding

class PermissionDialog(
    private val pendingPermissions: List<String>,
    private val onRequest: (String) -> Unit
) : DialogFragment() {
    private lateinit var binding: PermissionsViewpagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= PermissionsViewpagerBinding.inflate(inflater,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.permissionPager.adapter= PermissionPagerAdapter(pendingPermissions)

        binding.allowPermissionbotnowBtn.setOnClickListener {
            val currentItem = binding.permissionPager.currentItem
            val currentPermission = pendingPermissions[currentItem]
            onRequest(currentPermission)
            if(currentItem <pendingPermissions.size-1){
                binding.permissionPager.setCurrentItem(currentItem+1,true)


            }
            else{
                dismiss()
            }


        }
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }


}
