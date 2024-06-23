package com.papero.capstoneexpert.presentation

//import android.R
import android.R.menu
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MenuItemCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.papero.capstoneexpert.R
import com.papero.capstoneexpert.core.base.BaseActivity
import com.papero.capstoneexpert.databinding.ActivityMainBinding
import com.papero.capstoneexpert.presentation.home.HomeFragment
import com.papero.capstoneexpert.presentation.utilities.SearchViewHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var appbarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInsetsScreen(binding.main)

        val navHostFragment     = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController       = navHostFragment.navController
        appbarConfiguration = AppBarConfiguration(navController.graph)
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

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(com.papero.capstoneexpert.R.id.fragment_container).navigateUp(appbarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate = menuInflater
        inflate.inflate(com.papero.capstoneexpert.R.menu.menu_toolbar, menu)
        val searchView = menu?.findItem(com.papero.capstoneexpert.R.id.action_search)
        val i = MenuItemCompat.getActionView(searchView) as SearchView
//        SearchViewHelper.setOnFocus(binding.searchView)
        i.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.e("TAG", "onQueryTextChange: sumit ${query.toString()}")
                val b = findNavController(androidx.navigation.fragment.R.id.nav_host_fragment_container)
                val bun = Bundle()
                bun.putString(HomeFragment.QUERY_FILTER, query.toString())
                b.navigate(com.papero.capstoneexpert.R.id.homeFragment, bun)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("TAG", "onQueryTextChange: data $newText")
                val b = findNavController(androidx.navigation.fragment.R.id.nav_host_fragment_container)
                val bun = Bundle()
                bun.putString(HomeFragment.QUERY_FILTER, newText.toString())
                b.navigate(com.papero.capstoneexpert.R.id.homeFragment, bun)
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}