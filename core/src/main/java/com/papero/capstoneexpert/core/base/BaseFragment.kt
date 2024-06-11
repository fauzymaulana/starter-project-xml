package com.papero.capstoneexpert.core.base

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import com.papero.capstoneexpert.core.R

abstract class BaseFragment: Fragment() {

    fun isVisibleView(visibility: Int, vararg views: View) {
        for (view in views) {
            view.visibility = visibility
        }
    }

    fun isActionShimmer(action: (ShimmerFrameLayout) -> Unit, vararg shimmerView: ShimmerFrameLayout) {
        for (view in shimmerView) {
            action.invoke(view)
        }
    }

    fun stopShimmer(vararg views: ShimmerFrameLayout) {
        for (view in views){
            view.stopShimmer()
        }
    }

    fun startShimmer(vararg views: ShimmerFrameLayout) {
        for (view in views){
            view.startShimmer()
        }
    }

    fun hideKeyPad(rootView: ViewGroup?){
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(rootView?.windowToken, 0)
    }

    fun showKeypad(rootView: ViewGroup?){
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(rootView, InputMethodManager.SHOW_FORCED)
    }

    fun showSnackbar(view: View, message: String?) {
        val snackMessage = message ?: "Something went wrong"
        Snackbar.make(view, snackMessage, Snackbar.LENGTH_LONG).show()
    }

    fun showSnackBarwithAction(
        color: Int?,
        message: String?,
        actionMessage: CharSequence?,
        action: (View) -> Unit
    ) {
        val snackMessage = message ?: "Something went wrong"
        val snackBar = Snackbar.make(requireView(), snackMessage, Snackbar.LENGTH_LONG)
            .setBackgroundTint(
                ResourcesCompat.getColor(resources, color ?: com.google.android.material.R.color.m3_ref_palette_neutral20, null)
            )
            .setActionTextColor(
                resources.getColor(com.google.android.material.R.color.m3_chip_text_color, null)
            )
        if (actionMessage != null) {
            snackBar.setAction(actionMessage) {
                action(requireView())
            }.show()
        } else {
            snackBar.show()
        }
    }

    fun onBackPress(view: View?, viewLifecycleOwner: LifecycleOwner, action: (View) -> Unit) {
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.S) {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    action(requireView())
                }
            })
        } else {
            view?.apply {
                isFocusableInTouchMode = true
                requestFocus()
                setOnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP){
                        action(requireView())
                        return@setOnKeyListener  true
                    }
                    false
                }
            }
        }
    }

}