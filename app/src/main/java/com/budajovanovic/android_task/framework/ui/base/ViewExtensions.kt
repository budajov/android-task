package com.budajovanovic.android_task.framework.ui.base

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Fragment.showToast(
    message: String
) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Activity.showToast(
    message: String
) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}












