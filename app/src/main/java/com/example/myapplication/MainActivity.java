package com.example.myapplication;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.myapplication.Adapter.SectionsPagerAdapter;
import com.example.myapplication.Fragment.FragmentClub;
import com.example.myapplication.Fragment.FragmentHome;
import com.example.myapplication.Fragment.FragmentMine;

import java.util.ArrayList;
import java.util.List;
//haha

public class MainActivity extends AppCompatActivity
        implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    private ViewPager viewpager;
    private BottomNavigationBar bottomnavigationBar;

    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager = findViewById(R.id.viewpager);
        bottomnavigationBar = findViewById(R.id.bottom);
        initView();
    }

    private void initView() {
        initViewPager();
        initBottomNavigationBar();
    }

    private void initBottomNavigationBar() {

        // 配置底部导航栏
        bottomnavigationBar.setTabSelectedListener(this);
        bottomnavigationBar.clearAll();
        bottomnavigationBar.setMode(BottomNavigationBar.MODE_FIXED); // 自适应大小
        bottomnavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bottomnavigationBar.setBarBackgroundColor(R.color.white).setActiveColor(R.color.purple_700)
                .setInActiveColor(R.color.black);

        bottomnavigationBar
                .addItem(new BottomNavigationItem(R.drawable.homefill, "首页").setInactiveIconResource(R.drawable.home))
                .addItem(new BottomNavigationItem(R.drawable.club_balck, "专题")
                        .setInactiveIconResource(R.drawable.club))
                .addItem(new BottomNavigationItem(R.drawable.minefill, "我的").setInactiveIconResource(R.drawable.mine))
                .setFirstSelectedPosition(0).initialise();

    }

    private void initViewPager() {
        // 配置ViewPager
        viewpager.setOffscreenPageLimit(3);

        fragments = new ArrayList<Fragment>();
        fragments.add(new FragmentHome());
        fragments.add(new FragmentClub());
        fragments.add(new FragmentMine());

        viewpager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), fragments));
        viewpager.addOnPageChangeListener(this);
        viewpager.setCurrentItem(0);

    }

    @Override
    public void onTabSelected(int position) {
        viewpager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        bottomnavigationBar.selectTab(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}

