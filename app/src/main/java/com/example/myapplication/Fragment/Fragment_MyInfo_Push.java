package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Adapter.MyInfoPushAdapter;
import com.example.myapplication.Bean.Push_info;
import com.example.myapplication.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Fragment_MyInfo_Push extends Fragment {

    private SwipeRefreshLayout swipe_mypush;
    private RecyclerView rv_mypush;

    private TextView error_mypush;

    private String user_onlyid;

    private List<Push_info> data;

    private MyInfoPushAdapter myInfoPushAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myinfo_push,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取用户id
        final Intent in = getActivity().getIntent();
        user_onlyid = in.getStringExtra("user_onlyid");

        initView();
        Refresh();



        swipe_mypush.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipe_mypush.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }
    private  void Refresh(){
        BmobQuery<Push_info> query = new BmobQuery<>();
        query.addWhereEqualTo("UserOnlyId", user_onlyid);

        query.order("-createdAt");
        query.findObjects(new FindListener<Push_info>() {
            @Override
            public void done(List<Push_info> list, BmobException e) {
                swipe_mypush.setRefreshing(false);
                if(e == null){
                    data = list;
                    if (e==null){
                    data = list;
                    if (data.size()>0){
                        swipe_mypush.setVisibility(View.VISIBLE);
                        myInfoPushAdapter = new MyInfoPushAdapter(data,getActivity());
                        rv_mypush.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv_mypush.setAdapter(myInfoPushAdapter);
                    }else {
                        error_mypush.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(getActivity(), "加载失败"+e, Toast.LENGTH_SHORT).show();
                }
                }
            }
        });
    }


    private void initView() {
        error_mypush = getActivity().findViewById(R.id.error_mypush);
        rv_mypush = getActivity().findViewById(R.id.rv_mypush);
        swipe_mypush = getActivity().findViewById(R.id.swipe_mypush);
    }
}
