package com.example.murphysl.todo;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.murphysl.todo.db.dao.DaoMaster;
import com.example.murphysl.todo.db.dao.DaoSession;
import com.example.murphysl.todo.db.dao.NoteDao;
import com.example.murphysl.todo.db.entity.Todo;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * PlanAdapter
 *
 * @author: MurphySL
 * @time: 2016/11/8 9:00
 */


public class PlanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Todo> todos;
    private Context context;
    private View root;

    public PlanAdapter(Context context ,View root , List<Todo> todos){
        this.todos = todos;
        this.context = context;
        this.root = root;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlanViewHolder(LayoutInflater.from(context).inflate(R.layout.plan_item , parent , false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Todo todo = todos.get(position);
        final String data = todo.getCreateTime();
        final String title = todo.getTitle();
        final String content = todo.getContent();
        ((PlanViewHolder)holder).date.setText(data);
        ((PlanViewHolder)holder).title.setText(title);
        final ImageView select = ((PlanViewHolder)holder).select;
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select.setImageResource(R.drawable.ic_select_black_24dp);
            }
        });
        final View view = ((PlanViewHolder)holder).itemView;
        view.setOnClickListener(new View.OnClickListener() {
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

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "notes-db", null);
                SQLiteDatabase db = helper.getWritableDatabase();
                DaoMaster daoMaster = new DaoMaster(db);
                DaoSession daoSession = daoMaster.newSession();
                NoteDao noteDao = daoSession.getNoteDao();
                noteDao.delete(todo);
                Snackbar.make(root, "删除成功" , Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }


    public class PlanViewHolder extends RecyclerView.ViewHolder{

        public TextView date;
        public TextView title;
        public ImageView select;
        public View itemView;

        public PlanViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            date = (TextView) itemView.findViewById(R.id.plan_date);
            title = (TextView) itemView.findViewById(R.id.plan_title);
            select = (ImageView) itemView.findViewById(R.id.plan_select);
        }
    }
}
