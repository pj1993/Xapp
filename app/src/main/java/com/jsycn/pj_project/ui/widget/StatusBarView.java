package com.jsycn.pj_project.ui.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import static com.jsycn.pj_project.core.utils.StatusBarUtilsKt.getStatusBarHeight;


/**
 * @author zhangxiaowei 2018/5/21
 */
public class StatusBarView extends View {

    protected int mStatusBarHeight;

    public StatusBarView(Context context) {
        super(context);
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        try {
            getStatusBarHeight(ActivityUtils.getTopActivity(), new Function1<Integer, Unit>() {
                @Override
                public Unit invoke(Integer integer) {
                    mStatusBarHeight = integer;
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mStatusBarHeight);
        } else {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 0);
        }
    }
}
