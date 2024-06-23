package com.papero.capstoneexpert.presentation.utilities

import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import com.papero.capstoneexpert.R
import com.papero.capstoneexpert.presentation.utilities.SearchViewHelper.setMarginEnd

object SearchViewHelper {

    fun setOnFocus(view: SearchView) {
        view.setOnQueryTextFocusChangeListener { _, hasFocus ->
            view.setStyleFocus(hasFocus)
        }
    }

    fun SearchView.setStyleFocus(onFocus: Boolean) {
        if (onFocus) {
            this.setMarginEnd(22)
            this.setMarStart(16)
            background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_search_corner_8_white,
                null
            )
//            backgroundTintList = ResourcesCompat.getColorStateList(
//                resources,
//                R.color.white,
//                null
//            )
        } else {
            this.setMarginEnd(0)
            this.setMarStart(0)
            background = null
//            backgroundTintList = null
        }
    }

    private fun SearchView.setMarginEnd(num: Int) {
        val params = this.layoutParams as ViewGroup.MarginLayoutParams
        params.marginEnd = num
        this.layoutParams = params
    }

    private fun SearchView.setMarStart(num: Int) {
        val params = this.layoutParams as ViewGroup.MarginLayoutParams
        params.marginStart = num
        this.layoutParams = params
    }
}