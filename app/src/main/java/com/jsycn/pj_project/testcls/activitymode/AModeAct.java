package com.jsycn.pj_project.testcls.activitymode;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jsycn.pj_project.R;

public class AModeAct extends LifeLogActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_mode);
        Log.e("taskId","A---"+getTaskId());
        findViewById(R.id.tva).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AModeAct.this, BModeAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}

/**
 * @Deprecated  见 {@link  com.jsycn.pj_project.ui.activity.MainActivity } line 152
 */
//总结：
//A标准
//B可配置
//C：singleTask
//D:singleInstance
//
//1.singleInstance会给d创建一个单独进程，只会放d
//当A->B->D->B启动时，那么ABB是一个栈，D单独一个栈，回退时BBA，D就消失了。
//如果B也是singleTask，那么app栈只有AB，D单独一个栈，回退时BA。
//
//2.singleTask比较复杂，会清空栈顶，
//如果设置C为单独一个进程，那么C开启B，B会加入C的栈中，变成AB一个栈，CB一个栈。返回时BCBA。这里不会像singleInstance那样。
//如果将B设置成singleTask，那么情况就变了，启动时ABC-B时，不会创建B，AB一个栈，C一个栈，回退时BA就没了
//那么:    目前得出，B-C时  C的栈还没有出来的时候，启动C会加到AB上，不管B是不是singleInstance
//                  B-C时  如果C在宁一个栈有了，那么回退时，B就没了。（待验证，依据是 C单独一个栈时，B设置成singleTask时，C-B后，回退只有BA，没有C了）