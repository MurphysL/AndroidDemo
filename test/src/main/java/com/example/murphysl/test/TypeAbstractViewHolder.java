package com.example.murphysl.test;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * TypeAbstractViewHolder
 *
 * @author: MurphySL
 * @time: 2016/10/21 0:27
 */

public abstract class TypeAbstractViewHolder extends RecyclerView.ViewHolder{

    public TypeAbstractViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void binderHolder();
}
