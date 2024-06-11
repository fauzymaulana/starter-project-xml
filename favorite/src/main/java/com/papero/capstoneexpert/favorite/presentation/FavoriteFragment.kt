package com.papero.capstoneexpert.favorite.presentation

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.papero.capstoneexpert.core.base.BaseFragment
import com.papero.capstoneexpert.favorite.R
import com.papero.capstoneexpert.presentation.MainActivity

class FavoriteFragment : BaseFragment() {

    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).setTitleToolbar("Local Movie", false)
        (requireActivity() as MainActivity).showBottomNavigation(true)
    }
}