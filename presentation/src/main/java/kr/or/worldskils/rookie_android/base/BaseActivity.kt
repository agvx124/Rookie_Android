package kr.or.worldskils.rookie_android.base


import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import kr.or.worldskils.rookie_android.utils.SingleLiveEvent
import org.worldskils.cpu_z.ui.base.BaseFragment
import org.worldskils.cpu_z.ui.base.BaseViewModel

/**
 * Created by NA on 2020-04-16
 * skehdgur8591@naver.com
 */

abstract class BaseActivity<T: ViewDataBinding, VM: BaseViewModel<*>> : AppCompatActivity(),
    BaseFragment.CallBack {

    protected lateinit var binding: T
    protected lateinit var viewModel : VM

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract val viewModelClass: Class<VM>

    val onErrorEvent = SingleLiveEvent<Throwable>()

    /**
     * Binding 을 위한 함수
     */
    abstract fun getBindingVariable(): Int

    abstract fun setUp()

    abstract fun observerViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        setUp()
        observerViewModel()
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        this.viewModel = if (::viewModel.isInitialized) viewModel else ViewModelProvider(this).get(viewModelClass)
        binding.lifecycleOwner = this
        binding.setVariable(getBindingVariable(), viewModel)
        binding.executePendingBindings()
    }

    fun getViewDataBinding() : T {
        return binding
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }
}