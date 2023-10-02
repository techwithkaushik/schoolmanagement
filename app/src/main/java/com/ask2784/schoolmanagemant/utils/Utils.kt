package com.ask2784.schoolmanagemant.utils

class Utils {
    companion object {
        @JvmStatic
        fun capitalizeFirstLetter(str: String): String? {
            return str.trim().split("\\s+".toRegex())
                .joinToString(" ") { it.capitalize() }
        }
    }
}