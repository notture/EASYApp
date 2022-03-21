package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;


import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Bean.User;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;


import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Login extends AppCompatActivity {

    private EditText username, password;
    private Button login, zhuce;

    private ImageView lookps;
    private CheckBox read;
    private TextView protocal;
    private ImageView clear;

    private Boolean isPswVisible = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        zhuce = findViewById(R.id.zhuce);
        lookps = findViewById(R.id.lookps);
        clear = findViewById(R.id.clear);
        read = findViewById(R.id.read_ornot);
        protocal = findViewById(R.id.protocal);

        lookps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPswVisible();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
            }
        });

        protocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Protocal.class));
            }
        });


        //登录监听
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setUsername(username.getText().toString().trim());
                user.setPassword(password.getText().toString().trim());
                if(read.isChecked() == true)
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User o, BmobException e) {
                        if (e == null) {
//                                if(o.getEmailVerified()){
//                                Toast.makeText(Login.this, "登陆成功", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(Login.this, MainActivity.class));
//                                finish();
//                                }else{
//                                    Toast.makeText(Login.this, "请前往邮箱验证", Toast.LENGTH_SHORT).show();
//                                }
                            Toast.makeText(Login.this, "登陆成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Login.this, "登陆失败" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                else {
                    Toast.makeText(Login.this, "请阅读并勾选”用户协议“与”隐私政策“", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //注册监听
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });


    }

    private void setPswVisible() {
        isPswVisible = !isPswVisible;
        if (isPswVisible) {
            lookps.setImageResource(R.drawable.eyeopen);
            HideReturnsTransformationMethod method = HideReturnsTransformationMethod.getInstance();
            password.setTransformationMethod(method);
        } else {
            lookps.setImageResource(R.drawable.eyeclose);
            PasswordTransformationMethod method = PasswordTransformationMethod.getInstance();
            password.setTransformationMethod(method);
        }
    }


}
