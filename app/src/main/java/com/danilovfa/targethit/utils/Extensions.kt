package com.danilovfa.targethit.utils

import android.content.Context
import android.widget.Toast

val Any.TAG: String
get() {
    val tag = javaClass.simpleName
    return if (tag.length <= 23) tag else tag.substring(0..22)
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}