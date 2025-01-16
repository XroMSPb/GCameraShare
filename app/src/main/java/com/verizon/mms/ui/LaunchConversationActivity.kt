package com.verizon.mms.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.verizon.Preferences


class LaunchConversationActivity : AppCompatActivity() {
    private val prefs: Preferences by lazy {
        Preferences(baseContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (intent?.action) {
            Intent.ACTION_SEND -> {
                if (intent.type?.startsWith("image/") == true || intent.type?.startsWith("video/") == true) {
                    handleSendImage(intent)
                }
            }
        }
    }

    private fun handleSendImage(intent: Intent) {
        val uri: Uri? = intent.getParcelable(Intent.EXTRA_STREAM)
        uri?.let {
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, it)
                if (intent.type?.startsWith("image/") == true) {
                    type = "image/*"
                } else if (intent.type?.startsWith("video/") == true) {
                    type = "video/*"
                }

                setClassName(prefs.getPackageName(), prefs.getActivityName())
            }
            startActivity(Intent.createChooser(shareIntent, "Share with"))
            finishAffinity()
        }
    }

    private inline fun <reified T : Parcelable> Intent.getParcelable(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getParcelableExtra(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            getParcelableExtra(key)
        }
    }

}