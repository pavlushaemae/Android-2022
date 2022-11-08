package com.itis.androidcourse.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.itis.androidcourse.provider.NotificationProvider

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.run {
            NotificationProvider(this).showNotification("Проснитесь", "Пожалуйста")
        }
    }
}
