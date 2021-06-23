package kr.or.worldskils.rookie_android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.or.worldskils.rookie_android.R
import kr.or.worldskils.rookie_android.base.BaseActivity
import kr.or.worldskils.rookie_android.databinding.ActivityMainBinding

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

    }

    override fun observerViewModel() {

    }

}