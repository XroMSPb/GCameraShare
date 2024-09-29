package com.verizon.mms.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity


class LaunchConversationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (intent?.action) {
            Intent.ACTION_SEND -> {
                if (intent.type?.startsWith("image/") == true || intent.type?.startsWith("video/") == true) {
                    handleSendImage(intent) // Handle single image being sent
                }
            }
        }
    }

    private fun handleSendImage(intent: Intent) {
        (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
            //      img.setImageURI(it)
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, it)
                if(intent.type?.startsWith("image/") == true) {
                    type = "image/*"
                } else if(intent.type?.startsWith("video/") == true) {
                    type = "video/*"
                }

                setClassName("org.telegram.messenger.web","org.telegram.ui.LaunchActivity")
            }
            startActivity(Intent.createChooser(shareIntent, "Share with"))
            finishAffinity()
        }
    }
}