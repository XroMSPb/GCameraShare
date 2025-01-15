package com.verizon.messaging.vzmsgs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.verizon.Preferences
import com.verizon.messaging.vzmsgs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val prefs: Preferences by lazy {
        Preferences(baseContext)
    }

    private lateinit var appSelectorLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.link.setOnClickListener {
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(getString(R.string.link))
            }, null)
            startActivity(share)
        }

        appSelectorLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val packageName = data?.getStringExtra("packageName")
                val className = data?.getStringExtra("className")

                binding.packageName.setText(packageName)
                binding.activityName.setText(className)
            }
        }

        binding.packageName.setText(prefs.getPackageName())
        binding.activityName.setText(prefs.getActivityName())

        binding.activityName.setOnClickListener {
            startActivityForResult()
        }
        binding.packageName.setOnClickListener {
            startActivityForResult()
        }
    }

    fun startActivityForResult() {
        val intent = Intent(this, PackagesList::class.java)
        appSelectorLauncher.launch(intent)
    }

}