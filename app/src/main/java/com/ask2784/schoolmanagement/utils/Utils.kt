package com.ask2784.schoolmanagement.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

class Utils {
    companion object {
        
        @JvmStatic
        fun capitalizeFirstLetter(str: String): String? {
            return str.trim().split("\\s+".toRegex())
                .joinToString(" ") { it.capitalize() 
                }
        }
        
        @JvmStatic
        fun setupUI(view: View, activity: Activity) {
            // Set up touch listener for non-text box views to hide keyboard.
            if (view !is EditText) {
                view.setOnTouchListener { v, event ->
                    val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    if (inputMethodManager.isAcceptingText) {
                    inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken,0)
                    }
                    false
                }
            }
        
            // If a layout container, iterate over children and seed recursion.
            if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    val innerView: View = view.getChildAt(i)
                    setupUI(innerView,activity)
                }
            }
        }
    }
}