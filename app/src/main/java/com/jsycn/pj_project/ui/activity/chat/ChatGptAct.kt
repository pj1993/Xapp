package com.jsycn.pj_project.ui.activity.chat

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.BaseMultiItemAdapter
import com.didichuxing.doraemonkit.kit.core.TouchProxy.OnTouchEventListener
import com.jsycn.pj_project.R
import com.jsycn.pj_project.core.utils.getStatusBarHeight
import com.jsycn.pj_project.core.utils.setAndroidNativeLightStatusBar
import com.jsycn.pj_project.databinding.ActChatGptBinding
import com.jsycn.pj_project.databinding.ItemRvChatGpt1Binding
import com.jsycn.pj_project.databinding.ItemRvChatGpt2Binding

//https://zhuanlan.zhihu.com/p/343022200
class ChatGptAct : AppCompatActivity() {
    private lateinit var rootBind: ActChatGptBinding
    private val adapter = ChatAdapter(mutableListOf())
    private lateinit var heightChangeWatch: LineTextWatch

    private var mSoftInputHeight = 0//进入页面默认输入法关闭
    private val softInputListener: KeyboardUtils.OnSoftInputChangedListener =
        KeyboardUtils.OnSoftInputChangedListener { height -> //根据输入法高度，动态改变vFoot的高度
            //高度
            LogUtils.d(height)
            setFootHeight(height)
            mSoftInputHeight = height
        }

    private fun setFootHeight(height: Int) {
        if (mSoftInputHeight == height) return//说明输入法没有改变
        val animator = ValueAnimator.ofInt(mSoftInputHeight, height)
        animator.apply {
            addUpdateListener {
                //改变foot高度
                val lp = rootBind.vFoot.layoutParams
                lp.height = it.animatedValue as Int
                rootBind.vFoot.layoutParams = lp
            }
            //duration = 165
            duration = 155
            repeatCount = 0
            //interpolator = AccelerateInterpolator(2f)
            //interpolator = DecelerateInterpolator(1.1f)
        }.start()
        //滑动rv到底部
        if (height > 0)
            rootBind.rvChat.scrollToPosition(adapter.itemCount - 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //沉浸式
        setImmersive()
        super.onCreate(savedInstanceState)
        rootBind = ActChatGptBinding.inflate(layoutInflater)
        setContentView(rootBind.root)
        setStatus()
        setClick()
        //KeyboardUtils.registerSoftInputChangedListener(this, softInputListener)
        setInputListener()
        KeyboardUtils.fixSoftInputLeaks(this)
    }

    private fun setInputListener() {
        heightChangeWatch = LineTextWatch(rootBind.editTextInput) { scrollMsgToEnd() }
        rootBind.editTextInput.addTextChangedListener(heightChangeWatch)
        //1
        val deferringInsetsListener = RootViewDeferringInsetsCallback(
            persistentInsetTypes = WindowInsetsCompat.Type.systemBars(),
            deferredInsetTypes = WindowInsetsCompat.Type.ime()
        )
        ViewCompat.setWindowInsetsAnimationCallback(rootBind.vFoot, deferringInsetsListener)
        ViewCompat.setOnApplyWindowInsetsListener(rootBind.vFoot, deferringInsetsListener)
        //2
        ViewCompat.setWindowInsetsAnimationCallback(
            rootBind.clInput,
            TranslateDeferringInsetsAnimationCallback(
                view = rootBind.clInput,
                persistentInsetTypes = WindowInsetsCompat.Type.systemBars(),
                deferredInsetTypes = WindowInsetsCompat.Type.ime(),
                // We explicitly allow dispatch to continue down to binding.messageHolder's
                // child views, so that step 2.5 below receives the call
                dispatchMode = WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_CONTINUE_ON_SUBTREE
            )
        )
        //3
        val rvCallback = TranslateDeferringInsetsAnimationCallback(
            view = rootBind.rvChat,
            persistentInsetTypes = WindowInsetsCompat.Type.systemBars(),
            deferredInsetTypes = WindowInsetsCompat.Type.ime()
        )
        ViewCompat.setWindowInsetsAnimationCallback(rootBind.rvChat, rvCallback)
        ViewCompat.setOnApplyWindowInsetsListener(rootBind.rvChat) { _, _ ->
            scrollMsgToEnd()
            WindowInsetsCompat.CONSUMED
        }
        //4
        ViewCompat.setWindowInsetsAnimationCallback(
            rootBind.editTextInput,
            ControlFocusInsetsAnimationCallback(rootBind.editTextInput)
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        rootBind.editTextInput.removeTextChangedListener(heightChangeWatch)
        KeyboardUtils.unregisterSoftInputChangedListener(window)
    }

    private fun setImmersive() {
        //沉浸式：方案，透明状态栏，顶到头,设置占位状态栏view
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor = Color.TRANSPARENT
    }


    private fun setStatus() {
        //状态栏
        getStatusBarHeight(this) { height ->
            val params = rootBind.vStatus.layoutParams as LinearLayout.LayoutParams
            params.height = height
            rootBind.vStatus.layoutParams = params
            rootBind.vStatus.visibility = View.VISIBLE
        }
        setAndroidNativeLightStatusBar(this, true)
        window?.navigationBarColor = ContextCompat.getColor(this, R.color.titleAndBottomColor)
    }


    private fun setClick() {
        rootBind.rvChat.adapter = adapter
        val lm = LinearLayoutManager(this)
        /*lm.orientation = LinearLayoutManager.VERTICAL
        lm.reverseLayout = true*/
        rootBind.rvChat.layoutManager = lm
        /*rootBind.srlChat.setOnClickListener {
            KeyboardUtils.hideSoftInput(this)
        }*/
        rootBind.rvChat.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                KeyboardUtils.hideSoftInput(ChatGptAct@ this)
            }
            false
        }
        rootBind.tvSend.setOnClickListener {
            val msgT = rootBind.editTextInput.text.toString()
            if (msgT.isBlank()) return@setOnClickListener
            adapter.add(ChatEntity().apply { id = 1;msg = msgT })
            rootBind.editTextInput.setText("")
            scrollMsgToEnd()
        }
        addData()
    }

    private fun addData() {
        adapter.add(ChatEntity().apply { id = 1;msg = "你好啊！" })
        adapter.add(ChatEntity().apply { id = 2;msg = "你好啊！" })
        adapter.add(ChatEntity().apply { id = 1;msg = "我是chatgpt，我现在能听到你说的话" })
        adapter.add(ChatEntity().apply { id = 2;msg = "我是chatgpt，我现在能听到你说的话2" })
        adapter.add(ChatEntity().apply { id = 1;msg = "我是chatgpt，我现在能听到你说的话3" })
        adapter.add(ChatEntity().apply { id = 2;msg = "我是chatgpt，我现在能听到你说的话4" })
        adapter.add(ChatEntity().apply { id = 1;msg = "我是chatgpt，我现在能听到你说的话5" })
        adapter.add(ChatEntity().apply { id = 2;msg = "我是chatgpt，我现在能听到你说的话6" })
        adapter.add(ChatEntity().apply { id = 2;msg = "我是chatgpt，我现在能听到你说的话7" })
        adapter.add(ChatEntity().apply { id = 1;msg = "你好啊！" })
        adapter.add(ChatEntity().apply { id = 1;msg = "你好啊！" })
        adapter.add(ChatEntity().apply { id = 2;msg = "你好啊！" })
        adapter.add(ChatEntity().apply { id = 1;msg = "你好啊！" })
        adapter.add(ChatEntity().apply { id = 2;msg = "你好啊！" })
        adapter.add(ChatEntity().apply { id = 1;msg = "你好啊！" })
        scrollMsgToEnd()
    }

    private fun scrollMsgToEnd() {
        rootBind.rvChat.scrollToPosition(adapter.itemCount - 1)
    }


    /* override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
         if (ev.action == MotionEvent.ACTION_DOWN) {
             val v: View? = currentFocus
             if (isShouldHideKeyboard(v, ev)) {
                 KeyboardUtils.hideSoftInput(this)
             }
         }
         return super.dispatchTouchEvent(ev)
     }*/

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                KeyboardUtils.hideSoftInput(this)
            }
        }
        return super.onTouchEvent(ev)
    }

    // Return whether touch the view.
    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationOnScreen(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.rawX > left && event.rawX < right
                    && event.rawY > top && event.rawY < bottom);
        }
        return false
    }
}

//EditText行高监听
class LineTextWatch(val view: EditText, val onHeightChange: () -> Unit) : TextWatcher {
    private var lastLine = 0
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        lastLine = view.lineCount
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val cLine = view.lineCount
        if (cLine != lastLine) {
            onHeightChange.invoke()
            lastLine = cLine
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

}

//-------------------------------------------实体-----------------------------------------------
class ChatEntity {
    var id = 1//1代表自己
    var msg = ""
}

//------------------------------------------adapter----------------------------------------------------
class ChatAdapter(data: List<ChatEntity>) : BaseMultiItemAdapter<ChatEntity>(data) {
    class ItemVH1(val viewBinding: ItemRvChatGpt1Binding) :
        RecyclerView.ViewHolder(viewBinding.root)

    class ItemVH2(val viewBinding: ItemRvChatGpt2Binding) :
        RecyclerView.ViewHolder(viewBinding.root)

    init {
        addItemType(ITEM_TYPE_1, object : OnMultiItemAdapterListener<ChatEntity, ItemVH1> {
            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): ItemVH1 {
                val viewBinding =
                    ItemRvChatGpt1Binding.inflate(LayoutInflater.from(context), parent, false)
                return ItemVH1(viewBinding)
            }

            override fun onBind(holder: ItemVH1, position: Int, item: ChatEntity?) {
                holder.viewBinding.tvMsg.text = item?.msg
            }

            override fun isFullSpanItem(itemType: Int): Boolean {
                // 使用GridLayoutManager时，此类型的 item 是否是满跨度
                return true;
            }

        }).addItemType(ITEM_TYPE_2, object : OnMultiItemAdapterListener<ChatEntity, ItemVH2> {
            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): ItemVH2 {
                val viewBinding =
                    ItemRvChatGpt2Binding.inflate(LayoutInflater.from(context), parent, false)
                return ItemVH2(viewBinding)
            }

            override fun onBind(holder: ItemVH2, position: Int, item: ChatEntity?) {
                holder.viewBinding.tvMsg.text = item?.msg
            }


        }).onItemViewType { position, list ->
            if (list[position].id == 1) {
                ITEM_TYPE_1
            } else {
                ITEM_TYPE_2
            }
        }
    }

    companion object {
        private const val ITEM_TYPE_1 = 1//右边
        private const val ITEM_TYPE_2 = 2//左边
    }

}

