package com.verizon.messaging.vzmsgs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import com.verizon.Preferences
import com.verizon.Preferences.Companion.DEFAULT_ACTIVITY_NAME
import com.verizon.Preferences.Companion.DEFAULT_PACKAGE_NAME
import com.verizon.messaging.vzmsgs.databinding.ActivityMainBinding
import com.verizon.saveImageToPrivateStorage
import java.io.File

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val prefs: Preferences by lazy {
        Preferences(baseContext)
    }

    private lateinit var appSelectorLauncher: ActivityResultLauncher<Intent>
    private var packageName: String = ""
    private var className: String = ""
    private var appName: String = ""


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
                setPackageAndClassNames(
                    data?.getStringExtra("packageName") ?: DEFAULT_PACKAGE_NAME,
                    data?.getStringExtra("className") ?: DEFAULT_ACTIVITY_NAME,
                    data?.getStringExtra("appTitle") ?: "NoName"
                )
            }
        }

        binding.saveBtn.setOnClickListener {
            savePrefs()
        }
        binding.reset.setOnClickListener {
            saveImageToPrivateStorage(
                AppCompatResources.getDrawable(
                    baseContext,
                    R.mipmap.ic_launcher
                )!!.toBitmap(), baseContext
            )
            setPackageAndClassNames(DEFAULT_PACKAGE_NAME, DEFAULT_ACTIVITY_NAME, "Telegram")
            savePrefs()
        }

        setPackageAndClassNames(
            prefs.getPackageName(),
            prefs.getActivityName(),
            prefs.getAppName()
        )

        binding.activityName.setOnClickListener {
            startActivityForResult()
        }
        binding.packageName.setOnClickListener {
            startActivityForResult()
        }
    }

    private fun loadIconFile(): File {
        val filePath =
            File(baseContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "cache")
        if (!filePath.exists()) {
            filePath.mkdirs()
        }
        val file = File(filePath, "appIcon.webp")
        return file
    }

    private fun savePrefs() {
        prefs.setPackageName(packageName)
        prefs.setActivityName(className)
        prefs.setAppName(appName)
        Toast.makeText(this, R.string.settings_save, Toast.LENGTH_SHORT).show()
    }

    private fun setPackageAndClassNames(
        packageN: String,
        classN: String,
        appTitle: String = "App title"
    ) {
        packageName = packageN
        className = classN
        appName = appTitle
        binding.packageName.setText(packageName)
        binding.activityName.setText(className)
        binding.appTitle.text = appTitle
        binding.appIcon.setImageURI(loadIconFile().toUri())
    }

    private fun startActivityForResult() {
        val intent = Intent(this, PackagesList::class.java)
        appSelectorLauncher.launch(intent)
    }

}