package com.example.murphysl.imtest.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.murphysl.imtest.view.ImageLoaderFactory;
import com.example.murphysl.imtest.base.OnRecyclerViewListener;
import com.example.murphysl.imtest.R;
import com.example.murphysl.imtest.bean.UserBean;
import com.example.murphysl.imtest.view.UserInfoActivity;

import butterknife.Bind;

public class SearchUserHolder extends BaseViewHolder {

  @Bind(R.id.avatar)
  public ImageView avatar;
  @Bind(R.id.name)
  public TextView name;
  @Bind(R.id.btn_add)
  public Button btn_add;

  public SearchUserHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
    super(context, root, R.layout.item_search_user,onRecyclerViewListener);
    Log.i("IML" , "IML3");
  }

  @Override
  public void bindData(Object o) {
    Log.i("IML" , "IML4");
    final UserBean user =(UserBean)o;
    Log.i("IML" , "IML2");
    ImageLoaderFactory.getLoader().loadAvator(avatar,user.getAvatar(), R.mipmap.niyou);
    Log.i("IML" , "IML");
    name.setText(user.getUsername());
    btn_add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {//查看个人详情
          Bundle bundle = new Bundle();
          bundle.putSerializable("u", user);
          startActivity(UserInfoActivity.class,bundle);
        }
    });
  }


}