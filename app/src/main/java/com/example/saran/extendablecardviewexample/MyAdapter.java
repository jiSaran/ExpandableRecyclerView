package com.example.saran.extendablecardviewexample;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saran on 6/17/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<String> titleList;
    private List<String> contentList;
    private int expandedItem = -1;
    private OnCardViewChanged callback;
    private List<Boolean> expandedList;

    public MyAdapter(List<String> mTitleList, List<String> mContentList, OnCardViewChanged onCardViewChanged){
        titleList = mTitleList;
        contentList = mContentList;
        callback = onCardViewChanged;
        expandedList = new ArrayList<>();
        for(int i=0; i<mTitleList.size(); i++){
            expandedList.add(false);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if(expandedList.get(position)){
            holder.cvContent.expand();
            holder.ibSetting.setImageResource(R.drawable.ic_expand_less_black_24dp);
        } else {
            ViewGroup.LayoutParams layoutParams = holder.cvContent.getLayoutParams();
            final float scale = holder.cvContent.getContext().getResources().getDisplayMetrics().density;
            int height = (int) (44 * scale + 0.5f);
            layoutParams.height = height;
            holder.cvContent.setLayoutParams(layoutParams);
            holder.ibSetting.setImageResource(R.drawable.ic_expand_more_black_24dp);
        }
        holder.tvTitle.setText(titleList.get(position));
        holder.tvContent.setText(contentList.get(position));
        holder.ibSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expandedItem == -1){
                    holder.cvContent.expand();
                    expandedItem = holder.getAdapterPosition();
                    expandedList.set(holder.getAdapterPosition(),true);
                    holder.ibSetting.setImageResource(R.drawable.ic_expand_less_black_24dp);
                } else {
                    if(expandedList.get(holder.getAdapterPosition())){
                        holder.cvContent.collapse();
                        expandedList.set(holder.getAdapterPosition(),false);
                        holder.ibSetting.setImageResource(R.drawable.ic_expand_more_black_24dp);
                        expandedItem = -1;
                    } else {
                        callback.itemCollapse(expandedItem);
                        holder.ibSetting.setImageResource(R.drawable.ic_expand_less_black_24dp);
                        expandedList.set(holder.getAdapterPosition(),true);
                        expandedList.set(expandedItem,false);
                        holder.cvContent.expand();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                notifyItemChanged(expandedItem);
                                expandedItem = holder.getAdapterPosition();
                            }
                        },holder.cvContent.getAnimationTime());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MyCardView cvContent;
        TextView tvTitle;
        TextView tvContent;
        ImageButton ibSetting;
        public MyViewHolder(View itemView) {
            super(itemView);
            cvContent = (MyCardView)itemView.findViewById(R.id.cv_content);
            tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
            tvContent = (TextView)itemView.findViewById(R.id.tv_content);
            ibSetting = (ImageButton)itemView.findViewById(R.id.ib_setting);
        }
    }
}
