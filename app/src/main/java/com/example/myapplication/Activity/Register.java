package com.example.myapplication.Activity;



import android.app.Dialog;
import android.os.Bundle;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Bean.User;
import com.example.myapplication.Bean.focused_followed;
import com.example.myapplication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Register extends AppCompatActivity {

    private EditText username,password,email;
    private Button register,choosegender;

    private LinearLayout getman,getgril;

    private ImageView lookpass;

    private ImageView back;
    private ImageView reg_menu;

    private String mychoosegender = "man";

    private LinearLayout reg_help,reg_fankui,reg_quit;

    private Boolean isPswVisible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reg_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMenu();
            }
        });

        //对eye监听
        lookpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPswVisible();
            }
        });

        //对注册监听
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User();
                user.setUsername(username.getText().toString().trim());
                user.setPassword(password.getText().toString().trim());
                user.setEmail(email.getText().toString().trim());//email.getText().toString().trim()

                user.setGender(mychoosegender);
                if (username.getText().toString().equals("")){
                    username.setError("用户名没有正确输入");
                }else if (password.getText().toString().equals("")){
                    password.setError("密码没有正确输入");
                }
                if(email.getText().toString().equals("") || !isEmail(email.getText().toString().trim())){
                    email.setError("邮箱没有正确输入");
                }
                    else {

                        user.signUp(new SaveListener<User>() {
                            @Override
                            public void done(User user, BmobException e) {
                                if (e==null){
                                    focused_followed ff = new focused_followed();
                                    ff.setAuthor(user.getObjectId());
                                    ff.setFocused_sum(0);
                                    ff.setFollowed_sum(0);
                                    ff.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if(e == null) {

                                            }else {
                                                Toast.makeText(Register.this, "注册失败"+e, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(Register.this, "注册失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }


        });

        //设置性别监听
        choosegender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Register.this,R.style.BottomDialog);
                LinearLayout root = (LinearLayout) LayoutInflater.from(Register.this).inflate(R.layout.reg_choose,null);
                getman = root.findViewById(R.id.reg_man);
                getgril = root.findViewById(R.id.reg_girl);
                reg_quit = root.findViewById(R.id.reg_quit);
                getman.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mychoosegender = "man";
                        if (mychoosegender.toString().equals("man"))
                            choosegender.setText("男");
                        dialog.dismiss();
                    }
                });
                getgril.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mychoosegender = "gril";
                        if (mychoosegender.toString().equals("gril"))
                            choosegender.setText("女");
                        dialog.dismiss();
                    }
                });
                reg_quit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(root);
                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.x = 0;
                lp.y = 0;
                lp.width = getResources().getDisplayMetrics().widthPixels;
                root.measure(0,0);
                lp.height = root.getMeasuredHeight();
                lp.alpha = 9f;
                window.setAttributes(lp);
                dialog.show();

            }
        });



    }
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
    private void getMenu() {
        final Dialog dialog = new Dialog(Register.this,R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.reg_help,null);
        root.findViewById(R.id.reg_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册帮助
                Toast.makeText(Register.this, "成功", Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.reg_fankui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //反馈
            }
        });
        root.findViewById(R.id.reg_quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(root);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = 0;
        lp.width = getResources().getDisplayMetrics().widthPixels;
        root.measure(0,0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f;
        window.setAttributes(lp);
        dialog.show();

    }

    private void setPswVisible() {
        isPswVisible = !isPswVisible;
        if (isPswVisible){
            lookpass.setImageResource(R.drawable.eyeopen);
            HideReturnsTransformationMethod method = HideReturnsTransformationMethod.getInstance();
            password.setTransformationMethod(method);
        }else {
            lookpass.setImageResource(R.drawable.eyeclose);
            PasswordTransformationMethod method = PasswordTransformationMethod.getInstance();
            password.setTransformationMethod(method);
        }
    }

    private void initView(){
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);

        register = findViewById(R.id.register);

        choosegender = findViewById(R.id.choosegender);

        lookpass = findViewById(R.id.lookpass);

        back = findViewById(R.id.back);
        reg_menu = findViewById(R.id.reg_menu);
    };
}

