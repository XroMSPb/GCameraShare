package com.verizon.messaging.vzmsgs

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class AppInfo(
    val appName: String,
    val packageName: String,
    val className: String,
    val icon: Drawable
)

class AppAdapter(
    private val appList: List<AppInfo>,
    private val onItemClick: (AppInfo) -> Unit,
) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val appInfo = appList[position]
        holder.bind(appInfo, onItemClick)
    }

    override fun getItemCount(): Int = appList.size

    class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val appNameText: TextView = itemView.findViewById(R.id.package_name)
        private val classNameText: TextView = itemView.findViewById(R.id.class_name)
        private val icon: ImageView = itemView.findViewById(R.id.app_icon)

        fun bind(appInfo: AppInfo, onItemClick: (AppInfo) -> Unit) {
            appNameText.text = appInfo.appName
            classNameText.text = appInfo.className
            icon.setImageDrawable(appInfo.icon)
            itemView.setOnClickListener { onItemClick(appInfo) }
        }
    }
}
