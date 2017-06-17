package com.example.saran.extendablecardviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnCardViewChanged {

    RecyclerView rvMain;
    List<String> titleList;
    List<String> contentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvMain = (RecyclerView)findViewById(R.id.rv_main);

        setData();

        MyAdapter adapter = new MyAdapter(titleList,contentList,this);
        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvMain.setAdapter(adapter);
        rvMain.setLayoutManager(llm);
    }

    private void setData() {
        titleList = new ArrayList<>();
        contentList = new ArrayList<>();
        for(int i=0; i<20; i++){
            titleList.add("Title"+i);
            contentList.add("Content is "+i);
        }
    }

    @Override
    public void itemChanged(int position) {
        MyAdapter.MyViewHolder viewHolder = (MyAdapter.MyViewHolder) rvMain.findViewHolderForAdapterPosition(position);
        if(viewHolder!=null){
            viewHolder.cvContent.collapse();
            viewHolder.ibSetting.setImageResource(R.drawable.ic_expand_less_black_24dp);
        }
    }
}
