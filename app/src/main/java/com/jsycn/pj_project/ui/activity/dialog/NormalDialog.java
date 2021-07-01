package com.jsycn.pj_project.ui.activity.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.ColorInt;

import com.jsycn.pj_project.R;

/**
 * Created by pj on 2019/2/27.
 * 刺.刺.刺激
 * 次dialog包含提示dialog，两个按钮的确定取消dialog，一个输入框的dialog
 */
public class NormalDialog extends Dialog implements View.OnClickListener {
    private TextView txt_dialog_title;
    private TextView txt_dialog_tip;
    private EditText txt_input;
    private TextView btn_selectNegative;
    private TextView btn_selectPositive;
    private TextView btn_selectCenter;
    private String title,tip,negativeName,positiveName,centerName;
    private @ColorInt
    int negativeColor,positiveColor,titleColor,centerColor;
    private DialogOnClickListener negativeListener,positiveListener,centerListener;
    private int inputType;//输入框 的输入类型
    private boolean autoClose;//点击确定取消后是否关闭,默认是
    private int DIALOG_TYPE = 0;//dialog类型   0:单个确定按钮dialog  1:确定取消dialog  2:单个输入框dialog,3:三选
    public static final int DIALOG_CONFIRM=0,DIALOG_SELECT=1,DIALOG_ONEINPUT=2,THREE_SELECTION=3;
    private boolean touchOutsideCancel,cancelable;

    private NormalDialog(Builder builder) {
        super(builder.context);
        this.title=builder.title;
        this.tip=builder.tip;
        this.negativeName=builder.negativeName;
        this.positiveName=builder.positiveName;
        this.centerName=builder.centerName;

        this.negativeListener=builder.negativeListener;
        this.positiveListener=builder.positiveListener;
        this.centerListener=builder.centerListener;

        this.negativeColor=builder.negativeColor;
        this.positiveColor=builder.positiveColor;
        this.centerColor=builder.centerColor;
        this.titleColor=builder.titleColor;

        this.inputType=builder.inputType;
        this.autoClose=builder.autoClose;
        this.DIALOG_TYPE =builder.DIALOGTYPE;
        this.touchOutsideCancel =builder.touchOutsideCancel;
        this.cancelable =builder.cancelable;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置背景透明
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_normal);
        initView();
    }

    private void initView()   {
        //title
        txt_dialog_title = findViewById(R.id.txt_dialog_title);
        if(title!=null){txt_dialog_title.setText(title);}
        if(titleColor!=0) txt_dialog_title.setTextColor(titleColor);
        //tip
        txt_dialog_tip = findViewById(R.id.txt_dialog_tip);
        if(tip!=null) txt_dialog_tip.setText(tip);
        //negativeTX
        btn_selectNegative = findViewById(R.id.btn_selectNegative);
        if(negativeName!=null)btn_selectNegative.setText(negativeName);
        if(negativeColor!=0) btn_selectNegative.setTextColor(negativeColor);
        btn_selectNegative.setOnClickListener(this);
        //centerTX
        btn_selectCenter = findViewById(R.id.btn_center);
        if(centerName!=null)btn_selectCenter.setText(centerName);
        if(centerColor!=0) btn_selectCenter.setTextColor(centerColor);
        btn_selectCenter.setOnClickListener(this);
        //positiveTX
        btn_selectPositive = findViewById(R.id.btn_selectPositive);
        if(positiveName!=null) btn_selectPositive.setText(positiveName);
        if(positiveColor!=0) btn_selectPositive.setTextColor(positiveColor);
        btn_selectPositive.setOnClickListener(this);

        //设置输入框类型
        txt_input = findViewById(R.id.txt_input);
        if(inputType!=0) txt_input.setInputType(inputType);
        setCanceledOnTouchOutside(touchOutsideCancel);
        setCancelable(cancelable);
        getWindow().setWindowAnimations(R.style.iOSAnimStyle);
        switch (DIALOG_TYPE) {
            case DIALOG_CONFIRM://单选确定
                findViewById(R.id.split_vertical_1).setVisibility(View.GONE);
                findViewById(R.id.split_vertical_2).setVisibility(View.GONE);
                btn_selectNegative.setVisibility(View.GONE);
                btn_selectCenter.setVisibility(View.GONE);
                btn_selectPositive.setBackgroundResource(R.drawable.button_dialog_one);
                break;
            case DIALOG_SELECT://双选
                findViewById(R.id.split_vertical_1).setVisibility(View.GONE);
                btn_selectCenter.setVisibility(View.GONE);
                btn_selectPositive.setBackgroundResource(R.drawable.button_dialog_right);
                break;
            case DIALOG_ONEINPUT://带输入框 ，且左右两个按钮
                txt_input.setVisibility(View.VISIBLE);
                findViewById(R.id.split_vertical_1).setVisibility(View.GONE);
                btn_selectCenter.setVisibility(View.GONE);
                btn_selectPositive.setBackgroundResource(R.drawable.button_dialog_right);
                break;
            case THREE_SELECTION://三选

                break;
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_selectNegative){
            //取消
            if(negativeListener!=null) negativeListener.onClick(this,v);
        }else if(v.getId()==R.id.btn_selectPositive){
            //确定
            if(positiveListener!=null) positiveListener.onClick(this,v);
        }else if(v.getId()==R.id.btn_center){
            if(centerListener!=null) centerListener.onClick(this,v);
        }
        if(autoClose) dismiss();
    }
    public void setTip(String tip){
        txt_dialog_tip.setText(tip);
    }
    /**
     * 获取输入框的文本
     * @return m
     */
    public String getInputText(){
        return txt_input.getText().toString().trim();
    }

    public static class Builder{
        private Context context;
        private String title,tip,negativeName,positiveName,centerName;
        private @ColorInt
        int negativeColor,positiveColor,titleColor,centerColor;
        private DialogOnClickListener negativeListener,positiveListener,centerListener;
        private int inputType;
        private boolean autoClose=true;
        private int DIALOGTYPE=0;
        private boolean touchOutsideCancel =true,cancelable=true;
        public Builder setCanceledOnTouchOutside(boolean canceled){
            touchOutsideCancel =canceled;
            return this;
        }
        public Builder setCancelable(boolean cancelable){
            this.cancelable=cancelable;
            return this;
        }
        /**
         * 设置dialog的类型
         * @param DIALOG_TYPE DIALOG_CONFIRM=0,DIALOG_SELECT=1,DIALOG_ONEINPUT=2
         * @return m
         */
        public Builder setDialogType(int DIALOG_TYPE){
            this.DIALOGTYPE=DIALOG_TYPE;
            return this;
        }
        public Builder setTitle(String title){
            this.title=title;
            return this;
        }
        public Builder settitleColor(@ColorInt int color){
            this.titleColor=color;
            return this;
        }
        public Builder setTip(String tip){
            this.tip=tip;
            return this;
        }
        public Builder setNegativeName(String negativeName){
            this.negativeName=negativeName;
            return this;
        }
        public Builder setNegativeColor(@ColorInt int color){
            this.negativeColor=color;
            return this;
        }
        public Builder setNegativeListener(DialogOnClickListener negativeListener){
            this.negativeListener=negativeListener;
            return this;
        }
        public Builder setPositiveName(String positiveName){
            this.positiveName=positiveName;
            return this;
        }
        public Builder setPositiveColor(@ColorInt int color){
            this.positiveColor=color;
            return this;
        }
        public Builder setPositiveListener(DialogOnClickListener positiveListener){
            this.positiveListener=positiveListener;
            return this;
        }
        public Builder setCenterName(String centerName){
            this.centerName=centerName;
            return this;
        }
        public Builder setCenterColor(@ColorInt int color){
            this.centerColor=color;
            return this;
        }
        public Builder setCenterListener(DialogOnClickListener listener){
            this.centerListener=listener;
            return this;
        }
        /**
         * 是否自动关闭（点击确定和取消后）
         * @param autoClose 默认是
         * @return m
         */
        public Builder setAutoClose(boolean autoClose){
            this.autoClose=autoClose;
            return this;
        }
        /**
         * 设置输入框文本类型
         * @param type  InputType.TYPE_CLASS_TEXT  InputType.TYPE_CLASS_NUMBER InputType.TYPE_NUMBER_FLAG_DECIMAL
         *              InputType.TYPE_TEXT_VARIATION_PASSWORD
         * @return m
         */
        public Builder setInputType(int type){
            this.inputType=type;
            return this;
        }
        public NormalDialog build(Context context){
            this.context=context;
            return new NormalDialog(this);
        }

    }


}
