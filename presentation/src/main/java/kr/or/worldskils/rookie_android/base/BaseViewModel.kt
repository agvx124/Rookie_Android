package org.worldskils.cpu_z.ui.base

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kr.or.worldskils.rookie_android.utils.SingleLiveEvent

/**
 * Created by NA on 2020-04-16
 * skehdgur8591@naver.com
 */

abstract class BaseViewModel<N>(application: Application) : AndroidViewModel(application) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    val onErrorEvent = SingleLiveEvent<Throwable>()

    fun addDisposable(single: Single<*>, observer: DisposableSingleObserver<*>) {
        disposable.add(single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer as SingleObserver<Any>) as Disposable)
    }

    protected val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    fun getIsLoading(): LiveData<Boolean> {
        return isLoading
    }

    val baseObserver: DisposableSingleObserver<String>
        get() = object : DisposableSingleObserver<String>() {
            override fun onSuccess(s: String) {
                onRetrieveBaseSuccess(s)
                dispose()

            }

            override fun onError(e: Throwable) {
                onRetrieveError(e)
                dispose()
            }
        }

    val dataObserver: DisposableSingleObserver<N>
        get() = object : DisposableSingleObserver<N>() {
            override fun onSuccess(t: N) {
                onRetrieveDataSuccess(t)
                dispose()
            }

            override fun onError(e: Throwable) {
                onRetrieveError(e)
                dispose()
            }
        }

    protected open fun onRetrieveDataSuccess(data: N) {
    }

    protected open fun onRetrieveBaseSuccess(message: String) { }

    protected open fun onRetrieveError(e: Throwable) {
        errorEvent(e)
        isLoading.value = false
    }

    private fun checkNetworkConnected(): Boolean {
        val manager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = manager.activeNetworkInfo
        if (activeNetworkInfo == null) {
            onErrorEvent.value = Exception("네트워크를 연결해주세요")
            return false
        }
        return true
    }

    private fun errorEvent(e: Throwable) {
        if (checkNetworkConnected()) {
            onErrorEvent.value = e
        }
    }
}