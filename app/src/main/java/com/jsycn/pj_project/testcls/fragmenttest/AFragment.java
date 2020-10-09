package com.jsycn.pj_project.testcls.fragmenttest;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jsycn.pj_project.R;

public class AFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("aFragment","onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("aFragment","onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("aFragment","onCreateView");
        return inflater.inflate(R.layout.fragment_a,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("aFragment","onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("aFragment","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("aFragment","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("aFragment","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("aFragment","onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("aFragment","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("aFragment","onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("aFragment","onDetach");
    }
}
