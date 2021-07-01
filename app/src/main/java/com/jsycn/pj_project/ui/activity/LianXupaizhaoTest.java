package com.jsycn.pj_project.ui.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jsycn.pj_project.R;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;

/**
 * Created by pj on 2019/4/9.
 * 刺.刺.刺激
 */
public class LianXupaizhaoTest extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lianxupaizhao);
        findViewById(R.id.tv_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImg();
            }
        });

    }
    int i=0;
    public void getImg(){
        Album.camera(this) // Camera function.
                .image() // Take Picture.
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        i++;
                        if(i<=4){
                           new Thread(){
                               @Override
                               public void run() {
                                   super.run();
                                   SystemClock.sleep(500);
                                   getImg();
                               }
                           }.start();
                        }
                    }
                })
                .start();
//        Album.camera(this) // Camera function.
//                .image() // Take Picture.
//                .requestCode(2)
//                .listener(new AlbumListener<String>() {
//                    @Override
//                    public void onAlbumResult(int requestCode, @NonNull String result) {
//                       i++;
//                       if(i<=4){
//                           getImg();
//                       }
//                    }
//                    @Override
//                    public void onAlbumCancel(int requestCode) {
//                    }
//                })
//                .start();
    }
}
