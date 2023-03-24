package com.jsycn.pj_project.ui.widget.view;

import static com.jsycn.pj_project.core.utils.MyUtilsKt.dp2px;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.jsycn.pj_project.R;


/**
 * create by pj on 2019/7/15
 * 刺.刺.刺激...
 * 手写MaterialEditText
 */
public class MaterialEditText extends AppCompatEditText implements TextWatcher{
    Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float TEXT_SIZE=dp2px(12);
    private static final float TEXT_MARGIN=dp2px(2);
    private static final int TEXT_HORIZONTAL_OFFSET= (int) dp2px(5);
    private static final int TEXT_VERTICAL_OFFSET= (int) dp2px(17);
    private float hint_text_alpha;//hint文字的透明度0-1
    private boolean hint_text_status=false;//hint的显示状态
    private boolean is_using_hint=true;//是否需要使用material_hint

    private ObjectAnimator animator;

    private void setMaterialHintPadding(){
        Rect rect=new Rect();
        getBackground().getPadding(rect);
        if(is_using_hint){
            setPadding(getPaddingLeft(), (int) (rect.top+TEXT_SIZE+TEXT_MARGIN),getPaddingRight(),getPaddingBottom());
        }else {
            setPadding(getPaddingLeft(), rect.top,getPaddingRight(),getPaddingBottom());
        }
    }
    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public void init(Context context, AttributeSet attrs){
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        is_using_hint = typedArray.getBoolean(R.styleable.MaterialEditText_using_material_hint, true);
        typedArray.recycle();
        //设置hint的文字大小
        p.setTextSize(TEXT_SIZE);
        setMaterialHintPadding();
        addTextChangedListener(this);
    }
    public float getHint_text_alpha() {
        return hint_text_alpha;
    }

    public void setHint_text_alpha(float hint_text_alpha) {
        this.hint_text_alpha = hint_text_alpha;
        invalidate();
    }
    private ObjectAnimator getAnimator(){
        if(animator==null){
            animator = ObjectAnimator.ofFloat(MaterialEditText.this,"hint_text_alpha",0,1);
        }
        return animator;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //画文字
        if(is_using_hint){
            p.setAlpha((int) (0xff*hint_text_alpha));
            float vertical_offset=(1-hint_text_alpha)*TEXT_SIZE;
            canvas.drawText(getHint().toString(),TEXT_HORIZONTAL_OFFSET,TEXT_VERTICAL_OFFSET+vertical_offset,p);
        }
    }
    //是否使用material hint
    public void usingMaterialHint(boolean is_using_hint){
        this.is_using_hint=is_using_hint;
        //重绘padding
        setMaterialHintPadding();
    }

//-----------------------------文本监听---------------------------------
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(is_using_hint){
            if(hint_text_status&&TextUtils.isEmpty(getText())){
                hint_text_status=false;
                getAnimator().reverse();
            }else if(!hint_text_status&&!TextUtils.isEmpty(getText())){
                hint_text_status=true;
                getAnimator().start();
            }
        }
    }
    @Override
    public void afterTextChanged(Editable s) {
    }
}
