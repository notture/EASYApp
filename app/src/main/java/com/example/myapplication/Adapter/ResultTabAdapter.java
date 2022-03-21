package com.example.myapplication.Adapter;





import static com.example.myapplication.R.drawable.girl;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.Activity.Login;
import com.example.myapplication.Activity.Recive;
import com.example.myapplication.Bean.Push_info;
import com.example.myapplication.R;

import java.util.List;

import cn.bmob.v3.BmobUser;

public class ResultTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Push_info> data;

    private final int N_TYPE = 0;
    private final int F_TYPE = 1;

    private int Max_num = 15;  //预加载的数据  一共15条

    private Boolean isfootview = true;  //是否有footview
    String ffid;

    public ResultTabAdapter(Context context, List<Push_info> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_push,viewGroup,false);
        View footview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item,viewGroup,false);
        if (i == F_TYPE){
            return new RecyclerViewHolder(footview,F_TYPE);
        }else {
            return new RecyclerViewHolder(view,N_TYPE);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
        if (isfootview && (getItemViewType(i))== F_TYPE){
            //底部加载提示
            recyclerViewHolder.Loading.setText("加载中...");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Max_num += 8;
                    notifyDataSetChanged();
                }
            },2000);
        }else {

            //这是ord_item的内容
            final Push_info post = data.get(i);
            recyclerViewHolder.username.setText(post.getUsername());
            recyclerViewHolder.info.setText(post.getInfo());
            recyclerViewHolder.time.setText(post.getCreatedAt());
            recyclerViewHolder.title.setText(post.getTitle());

            if(post.getGender().equals("man")){
                recyclerViewHolder.gender.setImageResource(R.drawable.man);
            }
            else {
                recyclerViewHolder.gender.setImageResource(girl);
            }
            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = recyclerViewHolder.getBindingAdapterPosition();
                    if (BmobUser.getCurrentUser(BmobUser.class) != null){
                        Intent in = new Intent(context, Recive.class);
                        in.putExtra("username",post.getUsername());
                        in.putExtra("content",post.getInfo());
                        in.putExtra("time",post.getCreatedAt());
                        in.putExtra("title", post.getTitle());
                        in.putExtra("user_onlyid",post.getUserOnlyId());
                        in.putExtra("id",data.get(position).getObjectId());

                        context.startActivity(in);
                    }else {
                        Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, Login.class));
                    }
                }
            });
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (position == Max_num - 1){
            return F_TYPE;  //底部type
        }else {
            return N_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        if (data.size() < Max_num){
            return data.size();
        }
        return Max_num;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView username,info,time,title; //ord_item的TextView
        public TextView Loading;
        public ImageView gender;

        public RecyclerViewHolder(View itemview, int view_type) {
            super(itemview);
            if (view_type == N_TYPE){
                username = itemview.findViewById(R.id.username);
                info = itemview.findViewById(R.id.info);
                time = itemview.findViewById(R.id.time);
                gender = itemview.findViewById(R.id.gender);
                title = itemview.findViewById(R.id.title);
            }else if(view_type == F_TYPE){
                Loading = itemview.findViewById(R.id.footText);
            }
        }
    }
}


