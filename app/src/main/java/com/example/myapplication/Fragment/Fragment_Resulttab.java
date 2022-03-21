package com.example.myapplication.Fragment;





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

import com.example.myapplication.Adapter.HomeTabAdapter;
import com.example.myapplication.Bean.Push_info;
import com.example.myapplication.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Fragment_Resulttab extends Fragment {

    private SwipeRefreshLayout swipe_resulttab;
    private RecyclerView rv_resulttab;

    private TextView error_resulttab;



    private List<Push_info> data;

    private HomeTabAdapter homeTabAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hometab,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        initView();
        Refresh();



        swipe_resulttab.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipe_resulttab.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }
    private void Refresh() {
        BmobQuery<Push_info> Po = new BmobQuery<Push_info>();
        Po.order("-createdAt");
        Po.setLimit(1000);
        Po.findObjects(new FindListener<Push_info>() {
            @Override
            public void done(List<Push_info> list, BmobException e) {
                swipe_resulttab.setRefreshing(false);
                if (e == null){
                    data = list;
                    if(data.size()>0){
                        swipe_resulttab.setVisibility(View.VISIBLE);
                        homeTabAdapter = new HomeTabAdapter(getActivity(),data);
                        rv_resulttab.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv_resulttab.setAdapter(homeTabAdapter);
                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        error_resulttab.setVisibility(View.VISIBLE);
                    }

                }else {
                    swipe_resulttab.setRefreshing(false);
                    Toast.makeText(getActivity(), "获取数据失败"+e, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void initView() {
        error_resulttab = getActivity().findViewById(R.id.error_hometab);
        rv_resulttab = getActivity().findViewById(R.id.rv_hometab);
        swipe_resulttab = getActivity().findViewById(R.id.swipe_hometab);
    }
}
