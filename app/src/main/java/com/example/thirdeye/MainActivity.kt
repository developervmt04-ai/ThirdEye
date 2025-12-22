package com.example.thirdeye

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.example.thirdeye.data.localData.SecurityPrefs
import com.example.thirdeye.databinding.ActivityMainBinding
import com.example.thirdeye.permissions.DeviceAdminManager
import com.example.thirdeye.permissions.Permissions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SecurityPrefs
    private lateinit var permissions: Permissions
    private lateinit var deviceAdminManager: DeviceAdminManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = SecurityPrefs(this)
        deviceAdminManager = DeviceAdminManager(this)


        permissions = Permissions(this, deviceAdminManager)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController


        val navGraph: NavGraph = navController.navInflater.inflate(R.navigation.app_nav_graph)


        navGraph.setStartDestination(
            if (prefs.isFirstLaunch) R.id.languageSelectionFragment else R.id.homeFragment
        )

        navController.graph = navGraph
    }

    fun requestPermissions() {
        permissions.checkAndRequest()
    }
}
