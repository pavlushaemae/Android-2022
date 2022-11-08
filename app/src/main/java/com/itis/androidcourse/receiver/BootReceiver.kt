package com.itis.androidcourse.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.itis.androidcourse.provider.AlarmProvider
import java.util.*

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            context?.apply {
                AlarmProvider(
                    this,
                    Calendar.getInstance()
                ).createAlarmFromSP()
            }
        }
    }
}