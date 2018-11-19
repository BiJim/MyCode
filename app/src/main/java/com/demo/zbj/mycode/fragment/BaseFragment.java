package com.demo.zbj.mycode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by BiJim on 2018/10/8.
 */

public abstract class BaseFragment extends Fragment {
    protected View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(getLayoutId(),container,false);
        init();
        return rootView;
    }

    protected abstract int getLayoutId();

    protected void init(){

    }
}
