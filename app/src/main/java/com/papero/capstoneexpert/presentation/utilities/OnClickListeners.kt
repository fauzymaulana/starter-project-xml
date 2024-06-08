package com.papero.capstoneexpert.presentation.utilities

class OnClickListener<T>(val clickListener: (data: T) -> Unit) {
    fun onClick(data: T) = clickListener(data)
}