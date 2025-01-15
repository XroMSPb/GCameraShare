package com.verizon.messaging.vzmsgs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class AppInfo(val appName: String, val packageName: String, val className: String)

class AppAdapter(
    private val appList: List<AppInfo>,
    private val onItemClick: (AppInfo) -> Unit,
) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val appInfo = appList[position]
        holder.bind(appInfo, onItemClick)
    }

    override fun getItemCount(): Int = appList.size

    class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val appNameText: TextView = itemView.findViewById(android.R.id.text1)
        private val classNameText: TextView = itemView.findViewById(android.R.id.text2)

        fun bind(appInfo: AppInfo, onItemClick: (AppInfo) -> Unit) {
            appNameText.text = appInfo.appName
            classNameText.text = appInfo.className
            itemView.setOnClickListener { onItemClick(appInfo) }
        }
    }
}
