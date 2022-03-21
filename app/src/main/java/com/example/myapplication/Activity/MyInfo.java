package com.example.myapplication.Activity;



import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Bean.User;
import com.example.myapplication.Bean.focused_followed;
import com.example.myapplication.Fragment.Fragment_MyInfo_Push;
import com.example.myapplication.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MyInfo extends AppCompatActivity {

    private ImageView back;
    private TextView my_name,my_pushnum,my_comnum,usercreattime;

    private TextView info_title;

    private ImageView my_gender;

    private String user_onlyid;
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;
    //private FragmentStatePagerItemAdapter fragadapter;
    private TextView info_followed;
    private TextView info_focused;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        final Intent in = getIntent();
        user_onlyid = in.getStringExtra("user_onlyid");

        initView();

        //配置veiwpager
        viewPager.setOffscreenPageLimit(3);
        getmyInfo();
        getfocused_followed();
        initTab();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }



    private void initTab() {


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("帖子", Fragment_MyInfo_Push.class)
                .add("社团", Fragment_MyInfo_Push.class)//更改
                .create());

        viewPager.setAdapter(adapter);


        smartTabLayout.setViewPager(viewPager);


    }

    private void getothercomunitynum() {

//        BmobQuery<Comunity> query = new BmobQuery<>();
//        query.addWhereEqualTo("user_onlyid",user_onlyid);
//        query.include("user");
//        query.order("-createdAt");
//        query.findObjects(new FindListener<Comunity>() {
//            @Override
//            public void done(List<Comunity> list, BmobException e) {
//                if (e == null){
//                    String size = String.valueOf(list.size());
//                    my_comnum.setText(size);
//                }else {
//                    Toast.makeText(MyInfo.this, "查询帖数失败", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    private void getotherpushnum() {

//        BmobQuery<Post> query = new BmobQuery<>();
//        query.addWhereEqualTo("user_onlyid",user_onlyid);
//        query.include("author");
//        query.order("-createdAt");
//        query.findObjects(new FindListener<Post>() {
//            @Override
//            public void done(List<Post> list, BmobException e) {
//                if (e == null){
//                    String size = String.valueOf(list.size());
//                    my_pushnum.setText(size);
//                }else {
//                    Toast.makeText(MyInfo.this, "查询帖数失败", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    private void getotherInfo() {
//        BmobQuery<User> bmobQuery = new BmobQuery<>();
//        bmobQuery.include("follower_id");
//        bmobQuery.getObject(user_onlyid, new QueryListener<User>() {
//            @Override
//            public void done(User user, BmobException e) {
//                if (e==null){
//                    my_name.setText(user.getUsername());
//                    info_title.setText(user.getUsername());
//                    my_nickname.setText(user.getNickname());
//                    followeList_id = user.getFollower_id().getObjectId();
//                    if (user.getGender().equals("man")){
//                        my_gender.setImageResource(R.drawable.man);
//                    }else {
//                        my_gender.setImageResource(R.drawable.gril);
//                    }
//                }else {
//                    //Toast.makeText(MyInfo.this, "加载失败", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    private void getmycomumitynum() {
        //获取我创建的论坛数
//        User user = BmobUser.getCurrentUser(User.class);
//        BmobQuery<Comunity> bmobQuery = new BmobQuery<>();
//        bmobQuery.addWhereEqualTo("user",user);
//        bmobQuery.order("-createdAt");
//        bmobQuery.findObjects(new FindListener<Comunity>() {
//            @Override
//            public void done(List<Comunity> list, BmobException e) {
//                String size = String.valueOf(list.size());
//                my_comnum.setText(size);
//            }
//        });
    }

    private void getmypushnum() {
        //获取我发布的帖子数
//        User user = BmobUser.getCurrentUser(User.class);
//        BmobQuery<Post> bmobQuery = new BmobQuery<>();
//        bmobQuery.addWhereEqualTo("author",user);
//        bmobQuery.order("-createdAt");
//        bmobQuery.findObjects(new FindListener<Post>() {
//            @Override
//            public void done(List<Post> list, BmobException e) {
//                String size = String.valueOf(list.size());
//                my_pushnum.setText(size);
//            }
//        });
    }

    private void getmyInfo() {
        //获取我的个人信息(名字)

        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("objectId", user_onlyid);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e == null) {
                    for(int i = 0; i < list.size(); i++){
                        my_name.setText(list.get(i).getUsername());
                        info_title.setText(list.get(i).getUsername()+"的信息");
                        if (list.get(i).getGender().equals("man")){
                            my_gender.setImageResource(R.drawable.man);
                        }else {
                            my_gender.setImageResource(R.drawable.girl);
                        }
                    }
                }else{
                    Toast.makeText(MyInfo.this, "获取信息失败"+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getfocused_followed() {
        BmobQuery<focused_followed> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("author", user_onlyid);
        bmobQuery.findObjects(new FindListener<focused_followed>() {
            @Override
            public void done(List<focused_followed> list, BmobException e) {
                if(e == null){
                    for(int i = 0; i < list.size(); i++) {
                        info_focused.setText(Integer.toString(list.get(i).getFocused_sum()));
                        info_followed.setText(Integer.toString(list.get(i).getFollowed_sum()));
                    }
                }else{
                    Toast.makeText(MyInfo.this, "获取信息失败"+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void initView() {
        back = findViewById(R.id.back);
        my_name = findViewById(R.id.my_name);
//        my_pushnum = findViewById(R.id.my_pushnum);
//        my_comnum = findViewById(R.id.my_comnum);

//        usercreattime = findViewById(R.id.usercreattime);
        my_gender = findViewById(R.id.my_gender);
        info_title = findViewById(R.id.info_title);
        //focus = findViewById(R.id.focus);
        //editmyinfo = findViewById(R.id.editmyinfo);
        smartTabLayout = findViewById(R.id.myinfotab);
        viewPager = findViewById(R.id.myinfovp);
        info_focused = findViewById(R.id.info_focused);
        info_followed = findViewById(R.id.info_followed);
        //focus_or_not = findViewById(R.id.focus);
    }
}

