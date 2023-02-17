package com.jsycn.pj_project.ui.activity

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemAdapter
import com.didichuxing.doraemonkit.widget.bravh.BaseMultiItemQuickAdapter
import com.jsycn.pj_project.R
import com.jsycn.pj_project.core.utils.getStatusBarHeight
import com.jsycn.pj_project.core.utils.setAndroidNativeLightStatusBar
import com.jsycn.pj_project.databinding.ActChatGptBinding
import com.jsycn.pj_project.databinding.ItemRvChatGpt1Binding
import com.jsycn.pj_project.databinding.ItemRvChatGpt2Binding

class ChatGptAct : AppCompatActivity() {
    private lateinit var rootBind: ActChatGptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //沉浸式
        setImmersive()
        super.onCreate(savedInstanceState)
        rootBind = ActChatGptBinding.inflate(layoutInflater)
        setContentView(rootBind.root)
        setStatus()
        setClick()
    }

    private fun setImmersive(){
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
            val params = rootBind.vStatus.layoutParams as ConstraintLayout.LayoutParams
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
        rootBind.rvChat.adapter = ChatAdapter(data)
        rootBind.rvChat.layoutManager = LinearLayoutManager(this)
    }

}

class ChatEntity{
    var id = 1//1代表自己
    var msg = ""
}

class ChatAdapter(data:List<ChatEntity>) :BaseMultiItemAdapter<ChatEntity>(data){
    class ItemVH1(private val viewBinding :ItemRvChatGpt1Binding) :RecyclerView.ViewHolder(viewBinding.root)

    class ItemVH2(private val viewBinding : ItemRvChatGpt2Binding) :RecyclerView.ViewHolder(viewBinding.root)

    init {
        addItemType(ITEM_TYPE_1,object :OnMultiItemAdapterListener<ChatEntity,ItemVH1>{
            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): ItemVH1 {
                val viewBinding = ItemRvChatGpt1Binding.inflate(LayoutInflater.from(context),parent,false)
                return ItemVH1(viewBinding)
            }
            override fun onBind(holder: ItemVH1, position: Int, item: ChatEntity?) {

            }
            override fun isFullSpanItem(itemType: Int): Boolean {
                // 使用GridLayoutManager时，此类型的 item 是否是满跨度
                return true;
            }

        }).addItemType(ITEM_TYPE_2,object :OnMultiItemAdapterListener<ChatEntity,ItemVH2>{
            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): ItemVH2 {
                val viewBinding = ItemRvChatGpt2Binding.inflate(LayoutInflater.from(context),parent,false)
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

    companion object{
        private const val ITEM_TYPE_1 = 1//右边
        private const val ITEM_TYPE_2 = 2//左边
    }
}

