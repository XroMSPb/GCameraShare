package com.verizon

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit

class Preferences(context: Context) {
    var sharedPrefs: SharedPreferences = context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
    fun setPackageName(packageName: String) {
        sharedPrefs.edit {
            putString(PACKAGE_NAME_KEY, packageName)
        }
    }

    fun setActivityName(activityName: String) {
        sharedPrefs.edit {
            putString(ACTIVITY_NAME_KEY, activityName)
        }
    }

    fun getPackageName(): String {
        return sharedPrefs.getString(PACKAGE_NAME_KEY, DEFAULT_PACKAGE_NAME).toString()
    }

    fun getActivityName(): String {
        return sharedPrefs.getString(ACTIVITY_NAME_KEY, DEFAULT_ACTIVITY_NAME).toString()
    }


    companion object {
        private const val PACKAGE_NAME_KEY = "package_name"
        private const val ACTIVITY_NAME_KEY = "activity_name"
        private const val DEFAULT_ACTIVITY_NAME = "org.telegram.ui.LaunchActivity"
        private const val DEFAULT_PACKAGE_NAME = "org.telegram.messenger.web"
        const val PREFERENCES = "preferences"
    }
}