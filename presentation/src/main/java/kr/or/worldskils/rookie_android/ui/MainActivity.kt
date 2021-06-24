package kr.or.worldskils.rookie_android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.view.GravityCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import kr.or.worldskils.rookie_android.R
import kr.or.worldskils.rookie_android.base.BaseActivity
import kr.or.worldskils.rookie_android.databinding.ActivityMainBinding
import java.util.*

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

        binding.navigationView.setNavigationItemSelectedListener { item ->
            item.isChecked = true
            binding.drawerLayout.closeDrawers()

            true
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
}