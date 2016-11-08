package com.example.murphysl.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.murphysl.todo.db.entity.Todo;
import com.vipul.hp_hp.timelineview.TimelineView;

import java.util.List;

/**
 *
 * TimeLineAdapter
 *
 * @author: MurphySL
 * @time: 2016/10/30 14:37
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {

    private List<Todo> list;
    private LayoutInflater layoutInflater;
    private Context context;

    public TimeLineAdapter(Context context , List<Todo> list){
        this.list = list;
        this.context = context;

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item ,parent , false);
        TimeLineViewHolder viewHolder = new TimeLineViewHolder(view , viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {

        Todo todo = list.get(position);
        final String data = todo.getCreateTime();
        final String title = todo.getTitle();
        final String content = todo.getContent();

        holder.dateView.setText(list.get(position).getCreateTime());
        holder.textView.setText(list.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("data" , data);
                bundle.putString("title" , title);
                bundle.putString("content" , content);
                intent.putExtras(bundle);
                intent.setClass(context , DetailActivity.class);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    class TimeLineViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public TimelineView timelineView;
        public TextView dateView;
        public View itemView;

        public TimeLineViewHolder(View itemView , int viewType) {
            super(itemView);
            this.itemView = itemView;
            dateView = (TextView) itemView.findViewById(R.id.date);
            textView = (TextView) itemView.findViewById(R.id.text);
            timelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
            timelineView.initLine(viewType);
        }
    }
}
