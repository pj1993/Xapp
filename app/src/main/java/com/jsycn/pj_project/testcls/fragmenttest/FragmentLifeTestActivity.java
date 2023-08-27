package com.jsycn.pj_project.testcls.fragmenttest;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jsycn.pj_project.R;

        //fragment和activity不同，activity中onPause和onStop是分开的，中间夹的a的生命周期，而fragment是onDestroyView分开的。
        // 1 加了回退栈，b replace a，生命周期如下（只 destroyView ）
        //2023-03-15 19:26:56.174 23096-23096/com.jsycn.pj_project E/bFragment: onPause             //b
        //2023-03-15 19:26:56.174 23096-23096/com.jsycn.pj_project E/bFragment: onStop              //b
        //2023-03-15 19:26:56.175 23096-23096/com.jsycn.pj_project E/aFragment: onCreateView
        //2023-03-15 19:26:56.181 23096-23096/com.jsycn.pj_project E/aFragment: onActivityCreated
        //2023-03-15 19:26:56.181 23096-23096/com.jsycn.pj_project E/aFragment: onStart
        //2023-03-15 19:26:56.183 23096-23096/com.jsycn.pj_project E/bFragment: onDestroyView       //b
        //2023-03-15 19:26:56.183 23096-23096/com.jsycn.pj_project E/aFragment: onResume

        // 2 不加回退栈, b replace a，生命周期如下（会销毁fragment）
        //2023-03-15 19:29:33.794 23362-23362/com.jsycn.pj_project E/bFragment: onPause             //b
        //2023-03-15 19:29:33.794 23362-23362/com.jsycn.pj_project E/bFragment: onStop              //b
        //2023-03-15 19:29:33.795 23362-23362/com.jsycn.pj_project E/aFragment: onAttach
        //2023-03-15 19:29:33.795 23362-23362/com.jsycn.pj_project E/aFragment: onCreate
        //2023-03-15 19:29:33.795 23362-23362/com.jsycn.pj_project E/aFragment: onCreateView
        //2023-03-15 19:29:33.799 23362-23362/com.jsycn.pj_project E/aFragment: onActivityCreated
        //2023-03-15 19:29:33.800 23362-23362/com.jsycn.pj_project E/aFragment: onStart
        //2023-03-15 19:29:33.800 23362-23362/com.jsycn.pj_project E/bFragment: onDestroyView       //b
        //2023-03-15 19:29:33.801 23362-23362/com.jsycn.pj_project E/bFragment: onDestroy           //b
        //2023-03-15 19:29:33.801 23362-23362/com.jsycn.pj_project E/bFragment: onDetach            //b
        //2023-03-15 19:29:33.802 23362-23362/com.jsycn.pj_project E/aFragment: onResume

        //使用show 和 hide 不会走fragment的生命周期方法（只会在第一次show显示的时候会调用前6个），onHiddenChanged

        //如果以ViewPager的形式添加多个Fragment，滑动切换Fragment，那么Fragment切换的时候，生命周期也不会执行，setUserVisibleHint

public class FragmentLifeTestActivity extends AppCompatActivity implements View.OnClickListener {

    private AFragment aFragment;
    private BFragment bFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_life_test);
        findViewById(R.id.tv_showA).setOnClickListener(this);
        findViewById(R.id.tv_showB).setOnClickListener(this);
        findViewById(R.id.tv_replaceA).setOnClickListener(this);
        findViewById(R.id.tv_replaceB).setOnClickListener(this);
        findViewById(R.id.tv_add_fragment).setOnClickListener(this);
        findViewById(R.id.tv_remove_fragment).setOnClickListener(this);
        aFragment = new AFragment();
        bFragment = new BFragment();
//        addFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_showA:
                showFragment("a");
                break;
            case R.id.tv_showB:
                showFragment("b");
                break;
            case R.id.tv_replaceA:
                replaceFragment(aFragment);
                break;
            case R.id.tv_replaceB:
                replaceFragment(bFragment);
                break;
            case R.id.tv_add_fragment:
                addFragment();
                break;
            case R.id.tv_remove_fragment:
                removeFragment();
                break;
        }
    }

    private void addFragment() {
        FragmentManager sfm = getSupportFragmentManager();
        FragmentTransaction ft = sfm.beginTransaction();
        Fragment a = sfm.findFragmentByTag("A");
        ft.add(R.id.fl_content, aFragment, "A");
        ft.add(R.id.fl_content, bFragment, "B");
    }
    private void removeFragment(){
        getSupportFragmentManager().beginTransaction()
                .remove(aFragment).commit();
    }

    private void showFragment(String tag) {
        FragmentManager sfm = getSupportFragmentManager();
        FragmentTransaction ft = sfm.beginTransaction();
        Fragment a = sfm.findFragmentByTag("A");
        if (a == null) {
            ft.add(R.id.fl_content, aFragment, "A");
            ft.add(R.id.fl_content, bFragment, "B");
        }
        //
        if ("a".equals(tag)) {
            ft.show(aFragment).hide(bFragment).commit();
        } else {
            ft.show(bFragment).hide(aFragment).commit();
        }
    }

    private void replaceFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, f)
                //.addToBackStack(null)
                .commit();
    }

}