package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.myapplication.Bean.Push_info;
import com.example.myapplication.Fragment.Fragment_Hometab;
import com.example.myapplication.Fragment.Fragment_MyInfo_Push;
import com.example.myapplication.Fragment.Fragment_Resulttab;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ResultActivity extends AppCompatActivity {
    ImageView back;//返回按钮
    TextView mEditSearch;//不可编辑的搜索文本框

    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;
    private Spinner sp1,sp2;//两个下拉菜单

    private String[] starArray1 = {"默认排序","阅读多","新发布","评论多"};//第一个下拉菜单内容
    private String[] starArray2 = {"默认类型","只看文章","只看回答"};//第二个下拉菜单内容




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        initView();

        initmEditSearch();
        initSpinner();
        viewPager.setOffscreenPageLimit(3);
        initTab();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //Toast.makeText(ResultActivity.this, "回到主页", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void initTab(){
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("综合", Fragment_Resulttab.class)
                .add("问答", Fragment_Resulttab.class)//更改
                .create());

        viewPager.setAdapter(adapter);


        smartTabLayout.setViewPager(viewPager);
    }

    void initmEditSearch(){
        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        mEditSearch.setText(info);

        mEditSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }




    void initView(){
        back = findViewById(R.id.back);
        mEditSearch = findViewById(R.id.mEditSearch);
        smartTabLayout = findViewById(R.id.searchtab);
        viewPager = findViewById(R.id.searchvp);
        sp1 = (Spinner) findViewById(R.id.spinner1);
        sp2 = (Spinner) findViewById(R.id.spinner2);

    }


    private void initSpinner(){
        //声明一个下拉列表的数组适配器
        ArrayAdapter<String> starAdapter1 = new ArrayAdapter<String>(this,R.layout.item_select,starArray1);
        //设置数组适配器的布局样式
        starAdapter1.setDropDownViewResource(R.layout.item_dropdown);
        //从布局文件中获取名叫sp_dialog的下拉框

        //设置下拉框的标题，不设置就没有难看的标题了
        sp1.setPrompt("默认排序");
        //设置下拉框的数组适配器
        sp1.setAdapter(starAdapter1);
        //设置下拉框默认的显示第一项
        sp1.setSelection(0);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp1.setOnItemSelectedListener(new MySelectedListener1());
        sp1.setDropDownVerticalOffset(120);
        sp1.setDropDownHorizontalOffset(20);
        ArrayAdapter<String> starAdapter2 = new ArrayAdapter<String>(this,R.layout.item_select,starArray2);
        //设置数组适配器的布局样式
        starAdapter2.setDropDownViewResource(R.layout.item_dropdown);
        //从布局文件中获取名叫sp_dialog的下拉框

        //设置下拉框的标题，不设置就没有难看的标题了
        sp2.setPrompt("默认类型");
        //设置下拉框的数组适配器
        sp2.setAdapter(starAdapter2);
        //设置下拉框默认的显示第一项
        sp2.setSelection(0);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp2.setOnItemSelectedListener(new MySelectedListener2());
        sp2.setDropDownVerticalOffset(120);
        sp2.setDropDownHorizontalOffset(20);
    }

    class MySelectedListener1 implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            //Toast.makeText(ResultActivity.this,"您选择的是："+starArray1[i],Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    class MySelectedListener2 implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            //Toast.makeText(ResultActivity.this,"您选择的是："+starArray2[i],Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }




    }





