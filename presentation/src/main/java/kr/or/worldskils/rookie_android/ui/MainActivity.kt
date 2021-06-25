package kr.or.worldskils.rookie_android.ui

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kr.or.worldskils.rookie_android.R
import kr.or.worldskils.rookie_android.base.BaseActivity
import kr.or.worldskils.rookie_android.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import kotlin.math.*
import java.util.*
import kotlin.concurrent.thread


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getBindingVariable(): Int {
        return 0
    }

    override fun setUp() {
        setFirebase()
        setActionBar()
        binding.remainTextClock.text = getRemainTime()

        binding.navigationView.setNavigationItemSelectedListener { item ->
            item.isChecked = true
            binding.drawerLayout.closeDrawers()

            true
        }

        val handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                binding.remainTextClock.text = getRemainTime()
                binding.progressTextView.text = (round(getRemainProgress() * 100) / 100).toString() + "%"
                binding.progressbar.progress = round(getRemainProgress()).toInt()
            }
        }
        thread(start = true) {
            while (true) {
                Thread.sleep(1000)
                handler.sendEmptyMessage(0)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.openDrawer(GravityCompat.START)

        return super.onOptionsItemSelected(item)
    }

    override fun observerViewModel() {

    }

    private fun setFirebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun getToday(): Date {
        val cal = Calendar.getInstance()

        return cal.time
    }

    // 월요일 10시부터 시작.
    private fun getMonday(): Date {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        cal.set(Calendar.HOUR_OF_DAY, 12)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)

        return cal.time
    }

    private fun getFriday(): Date {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
        cal.set(Calendar.HOUR_OF_DAY, 12)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)

        return cal.time
    }

    private fun getRemainTime(): String {
        val remainTime = getFriday().time - getToday().time
        val diffSeconds = remainTime / 1000

        val diffHour = diffSeconds / (60 * 60)
        val diffMinute = diffSeconds / 60 - diffHour * 60
        val diffSecond = diffSeconds % 60

        val day = (remainTime/ (24*60*60*1000)).toInt()
        
        var hour = diffHour.toString()
        var minute = diffMinute.toString()
        var second = diffSecond.toString()

        if (diffHour < 10) {
            hour = "0$hour"
        }
        if (diffMinute < 10) {
            minute = "0$minute"
        }
        if (diffSecond < 10) {
            second = "0$second"
        }

        return if (day != 0) {
            "$day Day$hour : $minute : $second"
        } else {
            "$hour : $minute : $second"
        }
    }

    private fun getRemainPercent(): Double {
        val totalTime = getFriday().time - getMonday().time
        val remainTime = getFriday().time - getToday().time

        return 100.0 * remainTime / totalTime
    }

    private fun getRemainProgress(): Double {
        return 100.00 - getRemainPercent()
    }

    prinvate 
}