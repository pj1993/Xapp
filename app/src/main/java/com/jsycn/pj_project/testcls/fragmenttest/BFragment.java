package com.jsycn.pj_project.testcls.fragmenttest;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jsycn.pj_project.R;

public class BFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("bFragment","onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("bFragment","onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("bFragment","onCreateView");
        return inflater.inflate(R.layout.fragment_b,container,false);
    }
    
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("bFragment","onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("bFragment","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("bFragment","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("bFragment","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("bFragment","onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("bFragment","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("bFragment","onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("bFragment","onDetach");
    }
}
