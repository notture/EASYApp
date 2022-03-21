package com.example.myapplication.Activity;



import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Bean.User;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class Splash extends AppCompatActivity {

    private TextView sp_time;

    private int time = 5;

    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);   //快捷键：alt + enter

        initView();

        //延时操作
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                User user = BmobUser.getCurrentUser(User.class);
                if (user!=null){
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(Splash.this,Login.class));
                    finish();
                }
            }
        }, 3000);

        Bmob.initialize(Splash.this,"84078c04b530bea0a8ba92774d606afc");

        sp_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = BmobUser.getCurrentUser(User.class);
                if (user!=null){
                    timer.cancel();
                    startActivity(new Intent(Splash.this,MainActivity.class));
                }else {
                    timer.cancel();
                    startActivity(new Intent(Splash.this,Login.class));
                }
            }
        });


    }

    private void initView() {
        sp_time = findViewById(R.id.sp_time);
    }



}
