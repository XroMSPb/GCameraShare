package com.verizon.messaging.vzmsgs

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PackagesList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_packages_list)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val appList = getInstalledApps()

        recyclerView.adapter = AppAdapter(appList) { appInfo ->
            val resultIntent = Intent().apply {
                putExtra("packageName", appInfo.packageName)
                putExtra("className", appInfo.className)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun getInstalledApps(): List<AppInfo> {
        val pm: PackageManager = packageManager
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val resolveInfos = pm.queryIntentActivities(intent, 0)
        return resolveInfos.map { resolveInfo ->
            val appName = resolveInfo.loadLabel(pm).toString()
            val packageName = resolveInfo.activityInfo.packageName
            val className = resolveInfo.activityInfo.name
            AppInfo(appName, packageName, className)
        }.sortedBy { it.appName }
    }
}