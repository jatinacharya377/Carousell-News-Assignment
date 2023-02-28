package com.carousell.carousellnews.utils

import android.content.Context
import android.widget.Toast

fun Context.toastS(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}