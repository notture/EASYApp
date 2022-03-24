package com.example.myapplication.Fragment;



import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.Activity.MyInfo;
import com.example.myapplication.Activity.SearchActivity;
import com.example.myapplication.Adapter.HomeTabAdapter;
import com.example.myapplication.Bean.Push_info;
import com.example.myapplication.Bean.User;
import com.example.myapplication.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;


import java.util.List;

import cn.bmob.v3.BmobUser;

public class FragmentHome extends Fragment {

    private TextView homesearch;
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;
    private ImageView homeheadpic;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmenthome, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initView();
        viewPager.setOffscreenPageLimit(3);

        initTab();

        homeheadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = BmobUser.getCurrentUser(User.class);
                Intent intent = new Intent(getActivity(), MyInfo.class);
                intent.putExtra("user_onlyid",user.getObjectId());
                startActivity(intent);
            }
        });

        homesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "进入搜索页面", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });



    }
    private void initTab() {


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getActivity().getSupportFragmentManager(), FragmentPagerItems.with(getActivity())
                .add("推荐", Fragment_Hometab.class)
                .add("热门", Fragment_Hometab.class)//更改
                .create());

        viewPager.setAdapter(adapter);


        smartTabLayout.setViewPager(viewPager);


    }



    private void initView() {
        homesearch = getActivity().findViewById(R.id.mEditSearch);
        homeheadpic = getActivity().findViewById(R.id.home_head_pic);
        smartTabLayout = getActivity().findViewById(R.id.hometab);
        viewPager = getActivity().findViewById(R.id.homevp);

    }
}

