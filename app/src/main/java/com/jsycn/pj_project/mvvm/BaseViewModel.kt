package com.jsycn.pj_project.mvvm

import android.os.NetworkOnMainThreadException
import androidx.lifecycle.*
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.JsonParseException
import kotlinx.coroutines.*
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


typealias Block<T> = suspend () -> T     //可以在别名类指定类型，例如suspend CoroutineScope.() -> Unit -----但是此时的block不需要调用invoke 了，直接block（）
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit
typealias EmitBlock<T> = suspend LiveDataScope<T>.() -> T

/**
 * Created by WinWang on 2020/6/8
 * Description->
 * Subclasses must have a constructor which accepts [Application] as the only parameter , when you create [ViewModel] using reflection
 */
open class BaseViewModel : ViewModel(), MyLifecycleObserver {

    val loginStatusInvalid: MutableLiveData<Boolean> = MutableLiveData()

    val viewStatus: MutableLiveData<ViewStatusEnum> = MutableLiveData()


    protected fun launch(
            error: Error? = null,
            cancel: Cancel? = null,
            handleError: Boolean = true,
            block: Block<Unit>
    ): Job {
        return viewModelScope.launch {
            try {
                block.invoke()
                viewStatus.value = ViewStatusEnum.SUCCESS
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        if (handleError) {
                            onError(e)
                        }
                        error?.invoke(e)
                    }
                }
            }
        }
    }

    /**
     * @param dispatcher  设置线程，这里默认主线程是因为默认的方法通过suspend挂起有自身线程封闭机制，所以不需要创建多余的线程，预留字段，是为了给自己Jsoup框架子线程执行-防止崩溃
     * @param block 协程中执行
     * @return Deferred<T>
     */
    protected fun <T> async(
            dispatcher: CoroutineDispatcher = Dispatchers.Default,
            block: Block<T>
    ): Deferred<T> {
        return viewModelScope.async(dispatcher) { block.invoke() }
    }


    /**
     * 取消协程
     * @param job 协程job
     */
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

    /**
     * 省去每次创建liveData的烦恼，利用liveData的包装创建，直接传入block发送道对应的页面（此时用livedata的协程作用域，不需要用viewmodeScope，用的是liveDataScope）
     */
    fun <T> emit(
            error: Error? = null,
            cancel: Cancel? = null,
            handleError: Boolean = true,
            block: EmitBlock<T>
    ): LiveData<T> = liveData {
        try {
            emit(block())
            showSuccess()
        } catch (e: Exception) {
            when (e) {
                is CancellationException -> cancel?.invoke(e)
                else -> {
                    if (handleError) {
                        onError(e)
                    }
                    error?.invoke(e)
                }
            }
        }
    }

    /**
     * 统一处理错误
     * @param e 异常
     */
    private fun onError(e: Exception) {
        when (e) {
            is UnknownHostException -> {
                // 连接失败
                ToastUtils.showShort("网络连接失败")
                showNetWorkError()
            }
            is ConnectException -> {
                // 连接失败
                ToastUtils.showShort("网络连接失败")
                showNetWorkError()
            }
            is SocketTimeoutException -> {
                // 请求超时
                ToastUtils.showShort("网络请求超时")
                showNetWorkError()
            }

            is JsonParseException -> {
                // 数据解析错误
                ToastUtils.showShort("数据解析错误")
                showDataError()
            }
            is NetworkOnMainThreadException -> {
                ToastUtils.showShort("网络请求线程异常")
                showDataError()
            }
            else -> {
                // 其他错误
                e.message?.let {
                    ToastUtils.showShort(it)
                }
                showDataError()
            }
        }
    }

    fun showNetWorkError() {
        viewStatus.postValue(ViewStatusEnum.NETWORKERROR)
    }

    fun showDataError() {
        viewStatus.postValue(ViewStatusEnum.ERROR)
    }

    fun showEmpty() {
        viewStatus.postValue(ViewStatusEnum.EMPTY)
    }

    fun showSuccess() {
        viewStatus.postValue(ViewStatusEnum.SUCCESS)
    }

    override fun onCreate(source: LifecycleOwner) {}

    override fun onResume(source: LifecycleOwner) {}

    override fun onPause(source: LifecycleOwner) {
    }

    override fun onDestroy(source: LifecycleOwner) {
    }

}