package com.verizon.messaging.vzmsgs

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.verizon.messaging.vzmsgs.databinding.ActivityPackagesListBinding
import com.verizon.saveImageToPrivateStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PackagesList : AppCompatActivity() {

    private val binding: ActivityPackagesListBinding by lazy {
        ActivityPackagesListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.Main).launch {
            val apps = getInstalledApps(packageManager)
            updateUIWithApps(apps)
        }
    }

    private fun updateUIWithApps(apps: List<AppInfo>) {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.recyclerView.adapter = AppAdapter(apps) { appInfo ->
            val resultIntent = Intent().apply {
                putExtra("packageName", appInfo.packageName)
                putExtra("className", appInfo.className)
                putExtra("appTitle", appInfo.appName)
                saveImageToPrivateStorage(appInfo.icon.toBitmap(), baseContext)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }


    private suspend fun getInstalledApps(pm: PackageManager): List<AppInfo> {

        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val resolveInfos = pm.queryIntentActivities(intent, 0)
        return withContext(Dispatchers.IO) {
            resolveInfos.map { resolveInfo ->
                val appName = resolveInfo.loadLabel(pm).toString()
                val icon = resolveInfo.loadIcon(pm)
                val packageName = resolveInfo.activityInfo.packageName
                val className = resolveInfo.activityInfo.name
                AppInfo(appName, packageName, className, icon)
            }.sortedBy { it.appName }
        }
    }
}