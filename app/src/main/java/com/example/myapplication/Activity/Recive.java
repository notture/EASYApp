package com.example.myapplication.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Adapter.HomeTabAdapter;
import com.example.myapplication.Adapter.ReviewAdapter;
import com.example.myapplication.Bean.Push_info;
import com.example.myapplication.Bean.Review;
import com.example.myapplication.Bean.User;


import com.example.myapplication.Bean.focused_followed;
import com.example.myapplication.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class Recive extends AppCompatActivity {

    private TextView username, content, time, title;
    private ImageView back;

    private ImageView rec_collect;
    private ImageView review;

    private String user_onlyid;

    private CircleImageView recive_headpic;

    private List<Review> data;
    //关注按钮
    private Button focus_or_not;

    private boolean related = false;
    private boolean focused = false;
    User current_user = BmobUser.getCurrentUser(User.class);

    String ffid;
    String fffid;

    private SwipeRefreshLayout swipe_review;
    private RecyclerView rv_review;
    private TextView error_review;

    private ReviewAdapter reviewAdapter;

    private String id_push;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recive);
        final Intent in = getIntent();
        user_onlyid = in.getStringExtra("user_onlyid");

        initView();

        initData();

        getisrelated();

       getisfocused();


        Refresh();



        swipe_review.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipe_review.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });


        //监听返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recive_headpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Recive.this, MyInfo.class);
                it.putExtra("user_onlyid", user_onlyid);
                startActivity(it);
            }
        });


        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent it = new Intent(Recive.this, MyInfo.class);
//                it.putExtra("user_onlyid", user_onlyid);
//                startActivity(it);
            }
        });

        //关注按钮的监听
        focus_or_not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!focused) {

                    User info_user = new User();
                    info_user.setObjectId(user_onlyid);
                    BmobRelation relation = new BmobRelation();
                    relation.add(info_user);

                    focused_followed f = new focused_followed();
                    f.setObjectId(ffid);
                    f.setFocused(relation);
                    f.increment("focused_sum");

                    f.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                focus_or_not.setText("已关");
                                focused = true;
                                Toast.makeText(Recive.this, "关注成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(Recive.this, "关注失败"+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    User user = new User();
                    user.setObjectId(current_user.getObjectId());
                    BmobRelation re = new BmobRelation();
                    re.add(user);

                    focused_followed ff = new focused_followed();
                    ff.setObjectId(fffid);
                    ff.setFollowed(re);
                    ff.increment("followed_sum");
                    ff.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });






                } else {
                    User info_user = new User();
                    info_user.setObjectId(user_onlyid);
                    BmobRelation relation = new BmobRelation();
                    relation.remove(info_user);
                    focused_followed f = new focused_followed();
                    f.setObjectId(ffid);
                    f.setFocused(relation);
                    f.increment("focused_sum", -1);
                    f.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                focus_or_not.setText("关注");
                                focused = false;
                                Toast.makeText(Recive.this, "取消关注成功", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(Recive.this, "取消关注失败"+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    User user = new User();
                    user.setObjectId(current_user.getObjectId());
                    BmobRelation re = new BmobRelation();
                    re.remove(user);

                    focused_followed ff = new focused_followed();
                    ff.setObjectId(fffid);
                    ff.setFollowed(re);
                    ff.increment("followed_sum",-1);
                    ff.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });

                }


            }
        });

        review.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomizeDialog();
            }
        }));


        //收藏监听
        rec_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = getIntent();
                String Id = in.getStringExtra("id");
                BmobQuery<Push_info> bmobQuery = new BmobQuery<>();
                bmobQuery.getObject(Id, new QueryListener<Push_info>() {
                    @Override
                    public void done(Push_info pos, BmobException e) {
                        if (!related) {
                            Intent in = getIntent();
                            String Id = in.getStringExtra("id");
                            User user = BmobUser.getCurrentUser(User.class);
                            Push_info po = new Push_info();
                            po.setObjectId(Id);

                            BmobRelation relation = new BmobRelation();
                            relation.add(user);
                            po.setRelation(relation);
                            po.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        rec_collect.setImageResource(R.drawable.shoucang_black);
                                        related = true;
                                        Toast.makeText(Recive.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Recive.this, "收藏失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            BmobQuery<User> b = new BmobQuery<>();
                            b.getObject(BmobUser.getCurrentUser().getObjectId(), new QueryListener<User>() {
                                @Override
                                public void done(User user, BmobException e) {
                                    BmobRelation r = new BmobRelation();
                                    Push_info p = new Push_info();
                                    p.setObjectId(id_push);
                                    r.add(p);
                                    user.setRelation_Push(r);
                                    user.update(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                        }
                                    });
                                }
                            });
                        } else {
                            Intent in = getIntent();
                            String Id = in.getStringExtra("id");
                            User user = BmobUser.getCurrentUser(User.class);
                            Push_info po = new Push_info();
                            po.setObjectId(Id);

                            BmobRelation relation = new BmobRelation();
                            relation.remove(user);
                            po.setRelation(relation);
                            po.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        rec_collect.setImageResource(R.drawable.sc_normal);
                                        related = false;
                                        Toast.makeText(Recive.this, "已取消收藏", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Recive.this, "取消收藏失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            BmobQuery<User> b = new BmobQuery<>();
                            b.getObject(BmobUser.getCurrentUser().getObjectId(), new QueryListener<User>() {
                                @Override
                                public void done(User user, BmobException e) {
                                    BmobRelation r = new BmobRelation();
                                    Push_info p = new Push_info();
                                    p.setObjectId(id_push);
                                    r.remove(p);
                                    user.setRelation_Push(r);
                                    user.update(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
            }
        });

    }

    private void getisfocused() {
        BmobQuery<focused_followed> qff = new BmobQuery<focused_followed>();
        qff.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class).getObjectId());
        qff.findObjects(new FindListener<focused_followed>() {
            @Override
            public void done(List<focused_followed> list, BmobException e) {
                for(int i = 0; i < list.size(); i++) {
                    ffid =list.get(i).getObjectId();
                    BmobQuery<User> query = new BmobQuery<User>();
                    focused_followed f = new focused_followed();
                    f.setObjectId(ffid);
                    query.addWhereRelatedTo("focused", new BmobPointer(f));
                    query.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            for(int i = 0; i < list.size(); i++) {
                                if(list.get(i).getObjectId().equals(user_onlyid)){
                                    focus_or_not.setText("已关注");
                                    focused = true;
                                }
                            }
                            if(!focused){
                                focus_or_not.setText("关注");
                            }
                        }
                    });

                }
            }
        });
                    BmobQuery<focused_followed> qfff = new BmobQuery<focused_followed>();
                    qfff.addWhereEqualTo("author", user_onlyid);
                    qfff.findObjects(new FindListener<focused_followed>() {
                        @Override
                        public void done(List<focused_followed> list, BmobException e) {
                            for(int i = 0; i < list.size(); i++) {
                                fffid = list.get(i).getObjectId();
                            }
                        }
                    });

    }

    private void getisrelated() {
        Intent in = getIntent();
        String Id = in.getStringExtra("id");
        // 查询喜欢这个帖子的所有用户，因此查询的是用户表
        BmobQuery<User> query = new BmobQuery<User>();
        Push_info post = new Push_info();
        post.setObjectId(Id);
//likes是Post表中的字段，用来存储所有喜欢该帖子的用户
        query.addWhereRelatedTo("relation", new BmobPointer(post));
        query.findObjects(new FindListener<User>() {

            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {

                    for (int i = 0; i < object.size(); i++) {
                        if (object.get(i).getObjectId().equals(BmobUser.getCurrentUser(User.class).getObjectId())) {
                            related = true;
                            rec_collect.setImageResource(R.drawable.shoucang_black);
                            break;
                        }
                    }
                } else {

                }
            }

        });



    }


    private void initData() {

        //第二种
        Intent a = getIntent();
        Intent b = getIntent();
        Intent c = getIntent();
        String usernamea = a.getStringExtra("username");
        String contenta = a.getStringExtra("content");
        String timea = a.getStringExtra("time");
        String titlea = a.getStringExtra("title");
        username.setText(usernamea);
        content.setText(contenta);
        time.setText(timea);
        title.setText(titlea);

        id_push = a.getStringExtra("id");

    }

    private void initView() {
        username = findViewById(R.id.username);
        content = findViewById(R.id.content);
        time = findViewById(R.id.time);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        rec_collect = findViewById(R.id.rec_collect);
        focus_or_not = findViewById(R.id.focus_or_not);
        recive_headpic = findViewById(R.id.recive_headpic);
        swipe_review = findViewById(R.id.swipe_review);
        rv_review = findViewById(R.id.rv_review);
        error_review = findViewById(R.id.error_review);
        review = findViewById(R.id.review);
    }

    void Refresh(){
        BmobQuery<Review> Po = new BmobQuery<Review>();
        Push_info p = new Push_info();
        p.setObjectId(id_push);
        Po.addWhereEqualTo("Push", p);
        Po.order("-createdAt");
        Po.setLimit(1000);
        Po.findObjects(new FindListener<Review>() {
            @Override
            public void done(List<Review> list, BmobException e) {
                swipe_review.setRefreshing(false);
                if (e == null){
                    data = list;
                    if(data.size()>0){
                        swipe_review.setVisibility(View.VISIBLE);
                        reviewAdapter = new ReviewAdapter(Recive.this,data);
                        rv_review.setLayoutManager(new LinearLayoutManager(Recive.this));
                        rv_review.setAdapter(reviewAdapter);
                        Toast.makeText(Recive.this, "刷新成功", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        error_review.setVisibility(View.VISIBLE);
                    }

                }else {
                    swipe_review.setRefreshing(false);
                    Toast.makeText(Recive.this, "获取数据失败"+e, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void showCustomizeDialog() {
        /* @setView 装入自定义View ==> R.layout.dialog_customize
         * 由于dialog_customize.xml只放置了一个EditView，因此和图8一样
         * dialog_customize.xml可自定义更复杂的View
         */
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(Recive.this);
        final View dialogView = LayoutInflater.from(Recive.this)
                .inflate(R.layout.dialog_review,null);
        customizeDialog.setTitle("评论");
        customizeDialog.setView(dialogView);
        customizeDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取EditView中的输入内容
                        EditText text_review =
                                (EditText) dialogView.findViewById(R.id.text_review);
                        User u = BmobUser.getCurrentUser(User.class);
                        Review r = new Review();
                        r.setAuthor(u);
                        r.setUsername(u.getUsername());
                        r.setObjectId(u.getObjectId());
                        Push_info p = new Push_info();
                        p.setObjectId(id_push);
                        r.setPush(p);
                        r.setInfo(text_review.getText().toString().trim());
                        r.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e==null){
                                    Toast.makeText(Recive.this, "发布成功", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(Recive.this, "发布失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Refresh();
                    }
                });
        customizeDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        customizeDialog.show();

    }

}
