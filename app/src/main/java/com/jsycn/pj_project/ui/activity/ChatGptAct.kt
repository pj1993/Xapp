package com.jsycn.pj_project.ui.activity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseMultiItemAdapter
import com.jsycn.pj_project.R
import com.jsycn.pj_project.core.utils.getStatusBarHeight
import com.jsycn.pj_project.core.utils.setAndroidNativeLightStatusBar
import com.jsycn.pj_project.databinding.ActChatGptBinding
import com.jsycn.pj_project.databinding.ItemRvChatGpt1Binding
import com.jsycn.pj_project.databinding.ItemRvChatGpt2Binding


class ChatGptAct : AppCompatActivity() {
    private lateinit var rootBind: ActChatGptBinding
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
            duration = 165
            repeatCount = 0
            //interpolator = AccelerateInterpolator()
            interpolator = DecelerateInterpolator()
        }.start()
        //滑动rv到底部
        if (height >0)
            rootBind.rvChat.scrollToPosition(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //沉浸式
        setImmersive()
        super.onCreate(savedInstanceState)
        rootBind = ActChatGptBinding.inflate(layoutInflater)
        setContentView(rootBind.root)
        setStatus()
        setClick()
        KeyboardUtils.registerSoftInputChangedListener(this, softInputListener)
        KeyboardUtils.fixSoftInputLeaks(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        KeyboardUtils.unregisterSoftInputChangedListener(window)
    }

    private fun setImmersive() {
        //沉浸式：方案，透明状态栏，顶到头,设置占位状态栏view
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }
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
        val data = mutableListOf<ChatEntity>()
        data.add(ChatEntity().apply { id = 1;msg = "你好啊！" })
        data.add(ChatEntity().apply { id = 2;msg = "我是chatgpt，我现在能听到你说的话" })
        data.add(ChatEntity().apply { id = 2;msg = "我是chatgpt，我现在能听到你说的话2" })
        data.add(ChatEntity().apply { id = 2;msg = "我是chatgpt，我现在能听到你说的话3" })
        data.add(ChatEntity().apply { id = 2;msg = "我是chatgpt，我现在能听到你说的话4" })
        data.add(ChatEntity().apply { id = 2;msg = "我是chatgpt，我现在能听到你说的话5" })
        data.add(ChatEntity().apply { id = 2;msg = "我是chatgpt，我现在能听到你说的话6" })
        data.add(ChatEntity().apply { id = 2;msg = "我是chatgpt，我现在能听到你说的话7" })
        data.add(ChatEntity().apply { id = 1;msg = "你好啊！" })
        data.add(ChatEntity().apply { id = 1;msg = "你好啊！" })
        data.add(ChatEntity().apply { id = 1;msg = "你好啊！" })
        data.add(ChatEntity().apply { id = 1;msg = "你好啊！" })
        data.add(ChatEntity().apply { id = 1;msg = "你好啊！" })
        data.add(ChatEntity().apply { id = 1;msg = "你好啊！" })
        val adapter = ChatAdapter(data)
        rootBind.rvChat.adapter = adapter
        val lm = LinearLayoutManager(this)
        lm.orientation = LinearLayoutManager.VERTICAL
        lm.reverseLayout = true
        rootBind.rvChat.layoutManager = lm
        rootBind.srlChat.setOnClickListener {
            KeyboardUtils.hideSoftInput(this)
        }

    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_UP) {
            val v: View? = currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                KeyboardUtils.hideSoftInput(this)
            }
        }
        return super.dispatchTouchEvent(ev)
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

class ChatEntity {
    var id = 1//1代表自己
    var msg = ""
}

class ChatAdapter(data: List<ChatEntity>) : BaseMultiItemAdapter<ChatEntity>(data) {
    class ItemVH1(private val viewBinding: ItemRvChatGpt1Binding) :
        RecyclerView.ViewHolder(viewBinding.root)

    class ItemVH2(private val viewBinding: ItemRvChatGpt2Binding) :
        RecyclerView.ViewHolder(viewBinding.root)

    init {
        addItemType(ITEM_TYPE_1, object : OnMultiItemAdapterListener<ChatEntity, ItemVH1> {
            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): ItemVH1 {
                val viewBinding =
                    ItemRvChatGpt1Binding.inflate(LayoutInflater.from(context), parent, false)
                return ItemVH1(viewBinding)
            }

            override fun onBind(holder: ItemVH1, position: Int, item: ChatEntity?) {

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

