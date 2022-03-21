package com.example.myapplication.Fragment;



import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Activity.MyFocused;
import com.example.myapplication.Activity.MyInfo;
import com.example.myapplication.Activity.MyPush;
import com.example.myapplication.Activity.Setting;
import com.example.myapplication.Bean.User;
import com.example.myapplication.Bean.focused_followed;
import com.example.myapplication.R;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UploadFileListener;

public class FragmentMine extends Fragment {

    private TextView username;

//    private Button loginout;

    private LinearLayout myinfo;
    private LinearLayout mypush;
    private LinearLayout mycomunity;
    private LinearLayout mycollect;
    private LinearLayout setting;

    private LinearLayout followactivity;
    private LinearLayout focusactivity;

    private TextView myfocusnum;
    private Integer focus;
    private TextView fansnum;
    private Integer fans;
    private ImageView mine_gender,scan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentmine,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        //加载我的信息
        getMyinfo();


        getsum();


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }
        });


        //监听followactivity
        followactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent it = new Intent(getActivity(),MyFollower.class);
//                it.putExtra("objectId", BmobUser.getCurrentUser(User.class).getObjectId());
//                startActivity(it);
            }
        });

        //监听跳转到我关注的人的界面
        focusactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyFocused.class));
            }
        });


        myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到我的信息界面
                User user = BmobUser.getCurrentUser(User.class);
                Intent intent = new Intent(getActivity(), MyInfo.class);
                intent.putExtra("user_onlyid",user.getObjectId());
                startActivity(intent);
            }
        });

        mypush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyPush.class));
            }
        });

        mycomunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(),MyComunity.class));
            }
        });

        mycollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Setting.class));
            }
        });

    }

    private void getsum() {

        BmobQuery<focused_followed> query = new BmobQuery<>();
        query.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class).getObjectId());
        query.findObjects(new FindListener<focused_followed>() {
            @Override
            public void done(List<focused_followed> list, BmobException e) {
                for(int i = 0; i < list.size(); i++) {
                    fansnum.setText(Integer.toString(list.get(i).getFollowed_sum()));
                    myfocusnum.setText(Integer.toString(list.get(i).getFocused_sum()));
                }
            }
        });
    }





    private void getMyinfo() {
        //加载个人信息
        BmobUser bu = BmobUser.getCurrentUser(BmobUser.class);
        String Id = bu.getObjectId();
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(Id, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e==null){
                    username.setText(user.getUsername());
                    if (user.getGender().equals("man")){
                        mine_gender.setImageResource(R.drawable.man);
                    }else {
                        mine_gender.setImageResource(R.drawable.girl);
                    }
                }else {
                    Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        username = getActivity().findViewById(R.id.username);

//        loginout = getActivity().findViewById(R.id.loginout);
        myinfo = getActivity().findViewById(R.id.myinfo);
        mypush = getActivity().findViewById(R.id.mypush);
        mycomunity = getActivity().findViewById(R.id.mycomunity);
        mycollect = getActivity().findViewById(R.id.mycollect);
        mine_gender = getActivity().findViewById(R.id.mine_gender);
        fansnum = getActivity().findViewById(R.id.fansnum);
        setting = getActivity().findViewById(R.id.setting);
        myfocusnum = getActivity().findViewById(R.id.myfocusnum);
        followactivity = getActivity().findViewById(R.id.followactivity);
        focusactivity = getActivity().findViewById(R.id.focusactivity);
        scan = getActivity().findViewById(R.id.scan);
    }


    //再次来到这个界面
    @Override
    public void onResume() {
        super.onResume();
        getsum();
    }


}


