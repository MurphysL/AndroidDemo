package com.xbei.murphysl.recyclerviewexample;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xbei.murphysl.recyclerviewexample.Bean.GankioRandomBean;

import java.util.ArrayList;
import java.util.List;

/**
 * MyAdapter
 *
 * @author: MurphySL
 * @time: 2017/1/2 18:25
 */


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private static final String TAG = "MyAdapter";

    private Context context ;
    private View view;
    private List<GankioRandomBean.ResultsBean> data = new ArrayList<>();

    private MyViewHolder viewHolder;

    public interface OnItemClickListener{
        void OnItemClick(View view , int pos);
        void OnLongItemClick(View view , int pos);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public MyAdapter(Context context , List data){
        this.context = context;

        this.data = data;

        Log.i(TAG, "MyAdapter: " + data.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item , parent , false);//false
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Log.i(TAG, "onBindViewHolder: " + data.get(position).getDesc());
        holder.textView.setText(data.get(position).getDesc());

       /* if(onItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick(holder.itemView , position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.OnLongItemClick(holder.itemView , position);
                    return true;//拦截
                }
            });
        }*/
//        Log.i(TAG, "getItemCount: " + position);
//        if(position == getItemCount() - 1){
//            loadData();
//        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void loadData(){
        Snackbar.make(view , "已到达底部" , Snackbar.LENGTH_SHORT).show();
    }

  /*  public void addItem(int pos){
        notifyItemInserted(pos);
    }

    public void deleteItem(int pos){
        data.remove(data.get(pos));
        notifyItemRemoved(pos);
    }*/

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_tx);
        }
    }
}
