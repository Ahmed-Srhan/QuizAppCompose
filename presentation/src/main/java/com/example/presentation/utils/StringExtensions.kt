package com.example.presentation.utils

import androidx.core.text.HtmlCompat

fun String.decodeHtml(): String {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
}