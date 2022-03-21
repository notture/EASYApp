package com.example.myapplication.Activity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Bean.Push_info;
import com.example.myapplication.Bean.User;
import com.example.myapplication.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class PushContent extends AppCompatActivity {

    private EditText pushcontent,title;
    private ImageView back;
    private Button push;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushcontent);

        initView();



        //发帖操作
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pushcontent.getText().toString().isEmpty()){
                    Toast.makeText(PushContent.this, "请输入内容", Toast.LENGTH_SHORT).show();
                }else {
                    User user = BmobUser.getCurrentUser(User.class);
                    Push_info po = new Push_info();
                    po.setUsername(user.getUsername());
                    po.setGender(user.getGender());
                    po.setInfo(pushcontent.getText().toString());
                    po.setTitle(title.getText().toString());
                    po.setAuthor(user);
                    po.setUserOnlyId(user.getObjectId());
                    po.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                pushcontent.setText("");
                                Toast.makeText(PushContent.this, "发布成功", Toast.LENGTH_SHORT).show();

                                finish();
                            }else {
                                Toast.makeText(PushContent.this, "发布失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        pushcontent = findViewById(R.id.pushcontent);
        push = findViewById(R.id.push);
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
    }
}
