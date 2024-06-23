package com.papero.capstoneexpert.core.ui

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.papero.capstoneexpert.core.BuildConfig
import com.papero.capstoneexpert.core.R

fun ImageView.loadImageWithProgressBar(url: String?, progressBar: CircularProgressIndicator) {
    progressBar.visibility = View.VISIBLE

    Glide.with(context)
        .load(BuildConfig.BASE_URL_IMAGE + url)
        .fitCenter()
        .error(R.drawable.ill_thumbnail_empty)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: com.bumptech.glide.load.DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.GONE
                return false
            }
        })
        .into(this)
}

fun <T> TextView.setContent(content: T?){
    var label: String = ""
    val visible = if (content != null) {
        when(content) {
            is String -> {
                if (content == "") {
                    View.GONE
                } else {
                    label = content
                    View.VISIBLE
                }
            }
            is Int -> {
                label = content.toString()
                View.VISIBLE
            }
            is Float -> {
                label = content.toString()
                View.VISIBLE
            }
            else -> {
                View.GONE
            }
        }
    } else {
        View.GONE
    }
    text = label
    visibility = visible
}

fun countRound(value: Float?): String {
    return if (value != null) {
        "${Math.round(value * 10.0) / 10.0}"
    } else {
        ""
    }
}