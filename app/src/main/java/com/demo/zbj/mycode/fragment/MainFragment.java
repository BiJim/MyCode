package com.demo.zbj.mycode.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.demo.zbj.mycode.R;


public class MainFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager vp;

    public static MainFragment getInstance(){
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void init() {
        super.init();
        tabLayout = rootView.findViewById(R.id.main_tb);
        vp = rootView.findViewById(R.id.vp_main);

        for (int i = 1; i < 10; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("TAB" + i));
        }

    }
}
