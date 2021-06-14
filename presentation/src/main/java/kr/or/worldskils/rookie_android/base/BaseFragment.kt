package org.worldskils.cpu_z.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kr.or.worldskils.rookie_android.base.BaseActivity

/**
 * Created by NA on 2020-04-16
 * skehdgur8591@naver.com
 */


abstract class BaseFragment<T: ViewDataBinding, V: BaseViewModel<*>> : Fragment() {

    protected lateinit var binding: T
    protected lateinit var viewModel : V
    private var mActivity: BaseActivity<*, *>? = null

    abstract val viewModelClass: Class<V>

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getBindingVariable(): Int

    abstract fun setUp()

    abstract fun observerViewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is BaseActivity<*, *>) {
            mActivity = context
            mActivity?.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Fragment Option Menu 사용
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.viewModel = if (::viewModel.isInitialized) viewModel else ViewModelProvider(this).get(viewModelClass)
        binding.setVariable(getBindingVariable(), viewModel)
        binding.lifecycleOwner = this

        setUp()
        binding.executePendingBindings()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observerViewModel()
    }

    fun getBaseActivity() : BaseActivity<*, *>? {
        return mActivity
    }

    fun getViewDataBinding() : T {
        return binding
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    interface CallBack {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }
}