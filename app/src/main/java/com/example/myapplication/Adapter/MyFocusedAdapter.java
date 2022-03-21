package com.example.myapplication.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Bean.Push_info;
import com.example.myapplication.Bean.User;
import com.example.myapplication.R;

import java.util.List;


public class MyFocusedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<User> data;
    private final int N_TYPE = 0;
    private final int F_TYPE = 1;
    private int Max_num = 15;  //预加载的数据  一共15条
    private Boolean isfootview = true;  //是否有footview



    public MyFocusedAdapter(Context context, List<User> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.follower_item, viewGroup, false);
        View footview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item, viewGroup, false);
        if (i == F_TYPE) {
            return new RecyclerViewHolder(footview, F_TYPE);
        } else {
            return new RecyclerViewHolder(view, N_TYPE);
        }
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (isfootview && (getItemViewType(i))== F_TYPE){
            //底部加载提示
            final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
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
            final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
            final User user = data.get(i);
            recyclerViewHolder.username.setText(user.getUsername());
            if(user.getGender().equals("man"))
                recyclerViewHolder.gender.setImageResource(R.drawable.man);
            else
                recyclerViewHolder.gender.setImageResource(R.drawable.girl);


            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = recyclerViewHolder.getBindingAdapterPosition();

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

        public TextView username; //ord_item的TextView
        public ImageView gender;
        public TextView Loading;


        public RecyclerViewHolder(View itemview, int view_type) {
            super(itemview);
            if (view_type == N_TYPE){
                username = itemview.findViewById(R.id.username);
                gender = itemview.findViewById(R.id.gender);

            }else if(view_type == F_TYPE){
                Loading = itemview.findViewById(R.id.footText);
            }
        }
    }
}