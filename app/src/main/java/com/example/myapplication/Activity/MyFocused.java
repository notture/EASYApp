package com.example.myapplication.Activity;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Adapter.MyFocusedAdapter;
import com.example.myapplication.Bean.User;
import com.example.myapplication.Bean.focused_followed;
import com.example.myapplication.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class MyFocused extends AppCompatActivity {

    private RecyclerView myfocused_rv;
    private TextView myfocused_error;
    private SwipeRefreshLayout myfocused_swipe;

    private ImageView back;

    List<User> data;
    MyFocusedAdapter myFocusedAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfocused);

        initView();

        //初始刷新
        Refresh();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myfocused_swipe.setColorSchemeResources(R.color.orange,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        myfocused_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }

    private void Refresh() {

        BmobQuery<focused_followed> query = new BmobQuery<>();
        query.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class).getObjectId());
        query.findObjects(new FindListener<focused_followed>() {
            @Override
            public void done(List<focused_followed> list, BmobException e) {
                for(int i = 0; i < list.size(); i++) {
                    BmobQuery<User> bmobQuery = new BmobQuery<>();
                    focused_followed f = new focused_followed();
                    f.setObjectId(list.get(i).getObjectId());
                    bmobQuery.addWhereRelatedTo("focused", new BmobPointer(f));
                    bmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            myfocused_swipe.setRefreshing(false);
                            if (e == null) {
                                data = list;
                                if (data.size() > 0) {
                                    myfocused_swipe.setVisibility(View.VISIBLE);
                                    myFocusedAdapter = new MyFocusedAdapter(MyFocused.this, data);
                                    myfocused_rv.setLayoutManager(new LinearLayoutManager(MyFocused.this));
                                    myfocused_rv.setAdapter(myFocusedAdapter);

                                } else {
                                    myfocused_error.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(MyFocused.this, "加载失败" + e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });






    }




    private void initView() {
        myfocused_rv = findViewById(R.id.myfocused_rv);
        myfocused_error = findViewById(R.id.myfocused_error);
        myfocused_swipe = findViewById(R.id.myfocused_swipe);
        back = findViewById(R.id.back);
    }
}
