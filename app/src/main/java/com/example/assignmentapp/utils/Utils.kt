package com.example.assignmentapp.utils

import android.app.Activity
import android.text.format.DateFormat
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.gson.JsonSyntaxException
import java.net.UnknownHostException


fun convertToDate(dateInMilliseconds: Long, dateFormat: String?): String {
    return DateFormat.format(dateFormat, dateInMilliseconds).toString()
}

fun parseException(e: Throwable?): String {
    return when (e) {
        is UnknownHostException -> {
            "No internet available"
        }

        is JsonSyntaxException -> {
            //"We are having some trouble connecting you to the server right now."
            "We are having some issue right now."
        }

        is Exception -> {
            when {
                e.message?.contains("Unable to resolve host", ignoreCase = true) ?: false -> {
                    "No internet available"
                }

                else -> e.message ?: "No internet available"
            }
        }

        else -> e?.message ?: "No internet available"
    }

}


fun View.hideKeyboard() {
    val imm: InputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}