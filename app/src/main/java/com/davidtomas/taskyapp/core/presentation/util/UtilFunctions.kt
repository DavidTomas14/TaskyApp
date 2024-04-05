package com.davidtomas.taskyapp.core.presentation.util

fun String.toInitials(): String {
    val words = this.split(" ").filter { it.isNotBlank() }
    return when {
        words.size == 1 -> words[0].take(2).uppercase()
        words.size == 2 -> words[0].take(1).uppercase() + words[1].take(1).uppercase()
        words.size > 2 -> words[0].take(1).uppercase() + words.last().take(1).uppercase()
        else -> ""
    }
}