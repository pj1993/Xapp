package com.jsycn.pj_project.testcls.fragmenttest;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jsycn.pj_project.R;

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
                .addToBackStack(null)
                .commit();
    }

}