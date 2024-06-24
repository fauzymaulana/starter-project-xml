package com.papero.capstoneexpert.presentation.setting

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.papero.capstoneexpert.BuildConfig
import com.papero.capstoneexpert.R
import com.papero.capstoneexpert.core.base.BaseFragment
import com.papero.capstoneexpert.databinding.FragmentSettingBinding
import com.papero.capstoneexpert.presentation.MainActivity

class SettingFragment : BaseFragment() {

    private val viewModel: SettingViewModel by viewModels()
    private var _binding : FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLabelVersion()
    }

    private fun setLabelVersion() {
        binding.labelVersion.text = resources.getString(R.string.setting_label_version, BuildConfig.VERSION_NAME)
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).findViewById<SearchView>(R.id.searchView).visibility = View.GONE
    }
}