package com.itis.androidcourse

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.itis.androidcourse.databinding.ActivityMainBinding
import com.itis.androidcourse.provider.AlarmProvider
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var alarmProvider: AlarmProvider? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val cal = Calendar.getInstance()

        val sharedPref = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val date = sharedPref.getLong("calendar", 0)

        binding?.run {

            if (date != 0L) {
                cal.timeInMillis = date
            }
            tvDate.text = dateFormat.format(cal.time)
            tvTime.text = timeFormat.format(cal.time)
            alarmProvider = AlarmProvider(this@MainActivity, cal)

            val dateSetListener =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    tvDate.text = dateFormat.format(cal.time)
                }

            val timeSetListener =
                OnTimeSetListener { _, hourOfDay, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    cal.set(Calendar.MINUTE, minute)
                    cal.set(Calendar.SECOND, 0)
                    cal.set(Calendar.MILLISECOND, 0)
                    tvTime.text = timeFormat.format(cal.time)
                }

            tvDate.setOnClickListener {
                DatePickerDialog(
                    this@MainActivity, dateSetListener, cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            tvTime.setOnClickListener {
                TimePickerDialog(
                    this@MainActivity, timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE), true
                ).show()
            }

            btnStart.setOnClickListener {
                alarmProvider?.createAlarm()
                with(sharedPref.edit()) {
                    putLong("calendar", cal.timeInMillis)
                    apply()
                }
            }

            btnStop.setOnClickListener {
                alarmProvider?.cancelAlarm()

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        alarmProvider = null
    }
}