package com.example.assignmentapp.utils

import android.text.format.DateFormat


fun convertToDate(dateInMilliseconds: Long, dateFormat: String?): String {
    return DateFormat.format(dateFormat, dateInMilliseconds).toString()
}