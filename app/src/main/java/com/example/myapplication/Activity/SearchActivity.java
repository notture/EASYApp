package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Bean.HisRecord;
import com.example.myapplication.Bean.Hotspot;
import com.example.myapplication.R;
import com.nex3z.flowlayout.FlowLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import android.widget.TextView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    String array_dis[] = {"SUN","MON","TUE","WED","THU","FRI","SAT"};//发现信息
    List<String> array_his;
    FlowLayout flowLayout_his;//历史流式布局
    FlowLayout flowLayout_dis;//发现流式布局
    AutoCompleteTextView mEditSearch;//搜索输入文本框
    TextView news[] = new TextView[6];//流式布局组件
    TextView cleanHis;//清除历史信息组件
    Button cancel;//取消按钮

    List<Hotspot> hotspots;
    List<HisRecord> hisRecords;

    TextView his_unfold;
    TextView dis_visible;
    boolean unfold = false;
    boolean invisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();//初始化
        initHis();
        initHotspots();
        initDropDown();
        addListener();
        add_his_dis();

    }
    void initView(){
        flowLayout_his = findViewById(R.id.flowLayout_his);
        flowLayout_dis = findViewById(R.id.flowLayout_dis);
        cancel = findViewById(R.id.cancel);
        news[0] = findViewById(R.id.news_one);
        news[1] = findViewById(R.id.news_two);
        news[2] = findViewById(R.id.news_three);
        news[3] = findViewById(R.id.news_four);
        news[4] = findViewById(R.id.news_five);
        news[5] = findViewById(R.id.news_six);
        mEditSearch = this.findViewById(R.id.mEditSearch);
        cleanHis = findViewById(R.id.clean_his);
        his_unfold = findViewById(R.id.his_unfold);
        dis_visible = findViewById(R.id.dis_visible);
    }

    void initHis(){
        BmobQuery<HisRecord> bmobQuery = new BmobQuery<>();
        bmobQuery.order("-createdAt");
        bmobQuery.addWhereEqualTo("author", BmobUser.getCurrentUser().getObjectId());
        bmobQuery.findObjects(new FindListener<HisRecord>() {
            @Override
            public void done(List<HisRecord> list, BmobException e) {//数据要在里面定义，否则闪退，原因不明
                if (e==null){
                    hisRecords = list;
                    for(int i = 0; i < hisRecords.size(); i++) {
                        HisRecord hisRecord = hisRecords.get(i);
                        array_his = hisRecord.getHisre();
                        for (int j = 0; j < array_his.size(); j++) {//为历史记录添加小组件
                            TextView tv = new TextView(SearchActivity.this);
                            tv.setText(array_his.get(j));
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                            tv.setPadding(30,10,30,10);
                            tv.setBackgroundResource(R.drawable.shape_flow);
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mEditSearch.setText(tv.getText().toString().trim());
                                    search();
                                }
                            });
                            flowLayout_his.addView(tv);//普通添加
                        }

                    }
                }
            }
        });
    }

    void initHotspots(){
        BmobQuery<Hotspot> bmobQuery = new BmobQuery<Hotspot>();
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Hotspot>() {
            @Override
            public void done(List<Hotspot> list, BmobException e) {//数据要在里面定义，否则闪退，原因不明
                if (e==null){
                    hotspots = list;
                    for(int i = 0; i < 6; i++) {
                        Hotspot hotspot = hotspots.get(i);
                        news[i].setText(hotspot.getNews());
                        news[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mEditSearch.setText(hotspot.getNews());//更新搜索文本框内容
                                search();
                            }
                        });
                    }
                }
            }
        });
    }
    void initDropDown() {
        final ArrayList<String> resourseData = new ArrayList<String>();//下拉提示输入信息表
        resourseData.add("aaaaaaaaaaaaaaa");
        ArrayAdapter<String> adapter_ = new ArrayAdapter<String> (this,android.R.layout.simple_dropdown_item_1line,resourseData);
        mEditSearch.setAdapter(adapter_);//添加适配器
        mEditSearch.setThreshold(1);//输入1字符后开始匹配
        mEditSearch.setOnClickListener(new View.OnClickListener() {//点击后弹出下拉框
            @Override
            public void onClick(View v) {
                mEditSearch.showDropDown();
            }
        });
    }
    void addListener(){
        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {//搜索框添加监听器，手机确认键触发
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    if ("".equals(mEditSearch.getText().toString().trim())) {//如果输入为空，就搜索hint的信息
                        mEditSearch.setText(mEditSearch.getHint().toString().trim());
                        search();
                        return true;
                    }
                    // 搜索功能主体
                    else{//存在信息，进行搜索
                        search();
                        return true;
                    }
                }
                return false;
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {//取消按钮添加监听器
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cleanHis.setOnClickListener(new View.OnClickListener() {//取消按钮添加监听器，清空历史搜索记录
            @Override
            public void onClick(View view) {
                flowLayout_his.removeAllViews();
                BmobQuery<HisRecord> h = new BmobQuery<>();
                h.addWhereEqualTo("author", BmobUser.getCurrentUser().getObjectId());
                h.findObjects(new FindListener<HisRecord>() {
                    @Override
                    public void done(List<HisRecord> list, BmobException e) {
                        HisRecord HR;
                        for(int i = 0; i < list.size(); i++) {
                            HR = list.get(i);
                            List<String> temp;
                            temp = HR.getHisre();
                            temp.clear();
                            HR.setHisre(temp);
                            HR.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {

                                }
                            });


                        }
                    }
                });
            }
        });

        his_unfold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!unfold){
                    his_unfold.setText("收起");
                    flowLayout_his.setMaxRows(7);
                    unfold = true;
                }
                else {
                    his_unfold.setText("展开");
                    flowLayout_his.setMaxRows(2);
                    unfold = false;
                }
            }
        });

        dis_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!invisible){
                    dis_visible.setText("显示");
                    flowLayout_dis.setVisibility(View.INVISIBLE);
                    invisible = true;
                }
                else {
                    dis_visible.setText("隐藏");
                    flowLayout_dis.setVisibility(View.VISIBLE);
                    invisible = false;
                }
            }
        });

    }
    void add_his_dis() {

        for (int i = 0; i < array_dis.length; i++) {//为搜索发现添加小组件
            TextView tv = new TextView(this);
            tv.setText(array_dis[i]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            tv.setPadding(30,10,30,10);
            tv.setBackgroundResource(R.drawable.shape_flow);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mEditSearch.setText(tv.getText().toString().trim());
                    search();
                }
            });
            flowLayout_dis.addView(tv);//普通添加
        }
    }

    void search(){//执行页面跳转进行搜索

        Intent intent = new Intent(SearchActivity.this, ResultActivity.class);
        intent.putExtra("info", mEditSearch.getText().toString().trim());//传递搜索内容
        startActivity(intent);
        mEditSearch.setSelection(mEditSearch.getText().length());

        TextView tv = new TextView(this);
        tv.setText(mEditSearch.getText().toString().trim());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        tv.setPadding(30,10,30,10);
        tv.setBackgroundResource(R.drawable.shape_flow);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditSearch.setText(tv.getText().toString().trim());
                search();
            }
        });
        flowLayout_his.addView(tv,0);//执行搜索后给历史记录进行添加，添加到第0个
        //finish();

        BmobQuery<HisRecord> h = new BmobQuery<>();
        h.addWhereEqualTo("author", BmobUser.getCurrentUser().getObjectId());
        h.findObjects(new FindListener<HisRecord>() {
            @Override
            public void done(List<HisRecord> list, BmobException e) {
                HisRecord HR;
                for(int i = 0; i < list.size(); i++) {
                    HR = list.get(i);
                    List<String> temp;
                    temp = HR.getHisre();
                    temp.add(0,tv.getText().toString().trim());
                    HR.setHisre(temp);
                    HR.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });


                }
            }
        });

    }



}