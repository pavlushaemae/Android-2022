package com.itis.androidcourse.provider

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.itis.androidcourse.R
import com.itis.androidcourse.receiver.AlarmReceiver
import java.util.*

class AlarmProvider(private val context: Context, private val cal: Calendar) {
    private var alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private val intent = Intent(context, AlarmReceiver::class.java)
    private val pending = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE,
    )

    fun createAlarm() {
        cal.apply {
            Log.e("cal", this.toString())
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pending)
            Toast.makeText(context, R.string.alarm_added, Toast.LENGTH_SHORT).show()
        }
    }

    fun cancelAlarm() {
        alarmManager.cancel(pending)
        Toast.makeText(context, R.string.alarm_cancelled, Toast.LENGTH_SHORT).show()
    }

    fun createAlarmFromSP() {
        val sh = context.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val date: Long = sh.getLong("calendar", 0)
        if (date != 0L) {
            cal.timeInMillis = date
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                cal.timeInMillis,
                pending
            )
        }
    }
}