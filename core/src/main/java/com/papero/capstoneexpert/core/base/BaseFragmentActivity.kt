package com.papero.capstoneexpert.core.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar



abstract class BaseFragmentActivity: FragmentActivity(){
    fun getActivityContext(): Context? {
        return this
    }

    fun isCautionVisible(visibility: Int, vararg views: View) {
        for (view in views) {
            view.visibility = visibility
        }
    }

    fun showSnackbar(message: String?) {
        val snackMessage = message ?: "Something went wrong"
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, snackMessage, Snackbar.LENGTH_LONG).show()
    }

    fun showToast(message: String?) {
        val toastMessage = message ?: "Something went wrong"
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT)
            .show()
    }

    @SuppressLint("PrivateResource")
    fun showSnackBarwithAction(
        color: Int?,
        message: String?,
        actionMessage: CharSequence?,
        action: (View) -> Unit
    ) {
        val rootView = findViewById<View>(android.R.id.content)
        val snackMessage = message ?: "Something went wrong"
        val snackbar = Snackbar.make(rootView, snackMessage, Snackbar.LENGTH_LONG)
            .setBackgroundTint(
                ResourcesCompat.getColor(resources, color ?: com.google.android.material.R.color.m3_ref_palette_neutral20, null)
            )
        if (actionMessage != null) {
            snackbar.setAction(actionMessage) {
                action(rootView)
            }.show()
        } else {
            snackbar.show()
        }
    }
}