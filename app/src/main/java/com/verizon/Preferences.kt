package com.verizon

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit

class Preferences(private val context: Context) {
    private var sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
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

    fun getAppName(): String {
        return sharedPrefs.getString(APP_TITLE_KEY, DEFAULT_APP_NAME).toString()
    }

    fun setAppName(appName: String) {
        sharedPrefs.edit {
            putString(APP_TITLE_KEY, appName)
        }
    }

    companion object {
        private const val PACKAGE_NAME_KEY = "package_name"
        private const val ACTIVITY_NAME_KEY = "activity_name"
        private const val APP_TITLE_KEY = "app_name"
        const val DEFAULT_ACTIVITY_NAME = "org.telegram.ui.LaunchActivity"
        const val DEFAULT_APP_NAME = "Telegram"
        const val DEFAULT_PACKAGE_NAME = "org.telegram.messenger.web"
        const val PREFERENCES = "preferences"
    }
}