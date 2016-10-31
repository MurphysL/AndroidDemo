package com.example.murphysl.todo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vipul.hp_hp.timelineview.LineType;
import com.vipul.hp_hp.timelineview.TimelineView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * TimeLineAdapter
 *
 * @author: MurphySL
 * @time: 2016/10/30 14:37
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {

    private List<String> list;
    private LayoutInflater layoutInflater;

    public TimeLineAdapter(Context context , List<String> list){
        this.list = list;

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
        Long d = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        String s = format.format(date);

        holder.dateView.setText(s);
        holder.textView.setText(list.get(position));
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

        public TimeLineViewHolder(View itemView , int viewType) {
            super(itemView);

            dateView = (TextView) itemView.findViewById(R.id.date);
            textView = (TextView) itemView.findViewById(R.id.text);
            timelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
            timelineView.initLine(viewType);
        }
    }
}
