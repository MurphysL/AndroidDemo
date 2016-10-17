package com.example.murphysl.pinterestlayout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * MyAdapter
 *
 * @author: MurphySL
 * @time: 2016/10/15 20:06
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private List<Drawable> data;
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Integer> mHeight = new ArrayList<>();

    public interface OnItemClickListener{
        void onItemClick(View view , int pos);
        void onItemLongClick(View view , int pos);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public MyAdapter(Context context, List<Drawable> data) {
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
        for(int i = 0 ; i < data.size() ; i ++)
            mHeight.add((int)(100 + Math.random() * 300));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item , parent , false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        lp.height = mHeight.get(position);
        holder.itemView.setLayoutParams(lp);

        holder.imageView.setBackground(data.get(position));
        if(null != onItemClickListener){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView , layoutPosition);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView , layoutPosition);
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(int pos){
        Resources resources = context.getResources();
        Drawable drawable = resources.getDrawable(R.drawable.i0);
        data.add(drawable);
        notifyItemInserted(pos);
        notifyItemChanged(pos);
    }
    public void deleteItem(int pos){
        data.remove(pos);
        notifyItemRemoved(pos);
    }

}

class MyViewHolder extends RecyclerView.ViewHolder{

    public ImageView imageView;

    public MyViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.item_iv);
    }
}
