package com.papero.capstoneexpert

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.papero.capstoneexpert.core.base.BaseActivity
import com.papero.capstoneexpert.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInsetsScreen(binding.main)
    }
}