package com.jsycn.pj_project.core.mvvm

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 *Created by WinWang on 2020/8/25
 *Description->使用ViewBinding和具有能感知生命周期和获取viewModel的控件，如果泛型和Fragment或者Activity里面的Viewmodel相同，
 *             默认获取的就是外层的Fragment或者Activity的ViewModel
 * Tips-------->解决防止内部的ViewModel和外部的ViewModel相同的情况，可以通过koin的Quolifier来处理，在module中
 *              通过name注入不同的viewModel的别名来实现，具体参展TestActivity和MyViewlayout
 */
abstract class BaseVbViewComponent<VM : BaseViewModel, VB : ViewBinding> @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs)
        , MyLifecycleObserver {


    //loading框放在activity里面比较好点，不然每个view都会持有一个内部的loading对象
    //所有view争抢activity的dialog问题
    // 1重复弹出问题，
    // 2所有view数据加载完后消失
    // 3 activity的init的加载消失与单个view触发弹框消失的 区别（比如页面加载框，与一个提交按钮的框取消的区别）
    //思路 通过一个boolean字段，hasInit来判断页面是否加载完成，当hasInit位true时，才接收单个弹窗事件，否则得监听判断所有view是否加载成功。
    //    if(hasInit){
    //        可以弹出单个view请求的加载框，可以关闭单个view的加载框
    //    }else{
    //        拒绝单个view的框请求
    //    }

    private var mViewComponentCallBack: ViewComponentCallBack? =null
    lateinit var mBinding: VB
    protected lateinit var lifecycleOwner: LifecycleOwner
    private lateinit var viewModelStoreOwner: ViewModelStoreOwner
    open var mContext:Context = context

    protected val mViewModel:VM by lazy {
        val types = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        ViewModelProvider(viewModelStoreOwner).get<VM>(types[0] as Class<VM>)
    }

    init {
        //javaClass.superclass //不带泛型的超类
        val type = javaClass.genericSuperclass//获取带了泛型的超类
        if (type is ParameterizedType){
            val actualTypeArguments = type.actualTypeArguments
            val clazz = actualTypeArguments[1] as Class<VB>
            val method = clazz.getMethod("inflate",LayoutInflater::class.java)
            mBinding = method.invoke(null,LayoutInflater.from(context)) as VB
        }
        removeAllViews()
        addView(mBinding.root)
    }

    /**
     * 设置view的外部回调
     */
    fun setViewComponentCallBack(viewComponentCallBack: ViewComponentCallBack){
        this.mViewComponentCallBack = viewComponentCallBack
    }

    open fun init(){
        lifecycleOwner = this.findViewTreeLifecycleOwner()!!
        viewModelStoreOwner = this.findViewTreeViewModelStoreOwner()!!
        lifecycleOwner.lifecycle.addObserver(this)
        lifecycleOwner.lifecycle.addObserver(mViewModel)
    }

    override fun onCreate(source: LifecycleOwner) {
        if (useEventBus()){
            //注册eventBus监听EventBus.getDefault().register(this)
        }
        initView()
        initStatusObserve()
        initObserve()
        initData()
    }

    override fun onDestroy(source: LifecycleOwner) {
        if (useEventBus()){
            //EventBus.getDefault().unregister(this)
        }
        source.lifecycle.removeObserver(this)
        lifecycleOwner.lifecycle.removeObserver(mViewModel)
    }

    override fun onPause(source: LifecycleOwner) {
    }

    override fun onResume(source: LifecycleOwner) {
    }

    protected open fun useEventBus(): Boolean = false

    /**
     * 如果有需要初始化View数据
     * 比如初始化adapter等等
     */
    open fun initView() {
    }

    private fun initStatusObserve(){
        mViewModel.viewStatus.observe(lifecycleOwner, Observer {
            mViewComponentCallBack?.run {
                onViewNetLoadCallBack(it,this@BaseVbViewComponent)//将状态回调给activity或者fragment
            }
        })
    }

    /**
     * 观察viewModel的数据变化
     */
    open fun initObserve() {

    }

    /**
     * 普通加载数据
     */
    open fun initData() {
    }


    //----------------------------可能会需要到的东西，需要koin---------------------------------
//    protected val mViewModel: VM by lazy {
//        if (isDIViewModel()) {
//            val clazz = this.javaClass.kotlin.supertypes[0].arguments[0].type!!.classifier!! as KClass<VM>
//            viewModelStoreOwner.getViewModel<VM>(
//                    qualifier = if (!TextUtils.isEmpty(koinQualifier())) qualifier(koinQualifier()) else null,
//                    clazz = clazz
//            )
//        } else {
//            val types = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
//            ViewModelProvider(viewModelStoreOwner).get<VM>(types[0] as Class<VM>)
//        }
//    }
//    /**
//     * 是否才有依赖注入ViewModel
//     */
//    open fun isDIViewModel():Boolean =false
//    /**
//     * 设置Quolifier别名
//     */
//    open fun koinQualifier(): String = ""
}