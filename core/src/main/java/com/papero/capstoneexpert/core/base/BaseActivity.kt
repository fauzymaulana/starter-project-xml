package com.papero.capstoneexpert.core.base

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar


/**
 * KlikAdzkiSiapHadapiCPNS / com.klik.adzkia.base
 * Created by Fauzi Maulana (papero.mint@gmail.com)
 * On 11:13, Okt 31, 2022
 */

abstract class BaseActivity : AppCompatActivity() {

    fun setInsetsScreen(viewId: ConstraintLayout) {
        ViewCompat.setOnApplyWindowInsetsListener(viewId) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
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
