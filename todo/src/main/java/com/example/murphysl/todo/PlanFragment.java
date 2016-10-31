package com.example.murphysl.todo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * PlanFragment
 *
 * @author: MurphySL
 * @time: 2016/10/30 10:59
 */

public class PlanFragment extends Fragment{

    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.plan_fragment , container , false);
        imageView = (ImageView) root.findViewById(R.id.image);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        initPic();
    }

    private void initPic() {
        //getContext!!!!
        Picasso.with(getContext())
                .load("http://tu.ihuan.me/api/bing")
                .into(imageView);
    }
}
