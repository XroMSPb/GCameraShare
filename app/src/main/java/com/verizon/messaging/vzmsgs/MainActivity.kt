package com.verizon.messaging.vzmsgs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textLink = findViewById<TextView>(R.id.link)
        textLink.setOnClickListener {
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(getString(R.string.link))
            }, null)
            startActivity(share)
        }
        val textBox = findViewById<EditText>(R.id.newAppName)
        val applyBtn = findViewById<Button>(R.id.apply)
        applyBtn.setOnClickListener{
            textBox.text?.toString()?.let {
                setTitle(it)
            }
        }
    }
}