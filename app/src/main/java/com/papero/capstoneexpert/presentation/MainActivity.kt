package com.papero.capstoneexpert.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.papero.capstoneexpert.R
import com.papero.capstoneexpert.core.base.BaseActivity
import com.papero.capstoneexpert.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInsetsScreen(binding.main)

        val navHostFragment     = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController       = navHostFragment.navController
        val appbarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appbarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)
    }

    fun setTitleToolbar(title: String?, showBackIcon: Boolean) {
        binding.toolbar.title = title ?: "Movie"
//        supportActionBar?.setDisplayHomeAsUpEnabled(showBackIcon)
        binding.toolbar.navigationIcon = if (showBackIcon) ResourcesCompat.getDrawable(resources, R.drawable.ic_arrow_back, null) else null
    }

    fun showBottomNavigation(show: Boolean) {
        binding.bottomNavigation.visibility = if (show) View.VISIBLE else View.GONE
    }
}