package com.papero.capstoneexpert.presentation.utilities

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.papero.capstoneexpert.R
import com.papero.capstoneexpert.databinding.AlertDialogLoadingRoundedBinding

class GeneralPopUp(private val context: Context) {
    private val loadingRounded by lazy { AlertDialogLoadingRoundedBinding.inflate(LayoutInflater.from(context)) }

    fun setupLoadingRounded(
        callback: (Dialog) -> Unit
    ) {
        loadingRounded.apply {
            val dialog = Dialog(context, R.style.DialogOverlayTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(root)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.create()
            dialog.show()
            callback(dialog)
        }
    }
}