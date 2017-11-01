package com.quickcanteen.quickcanteen.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.fragment.deliverOrder.DeliverOrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliverFragment extends Fragment {

    private TabLayout tablayout;
    private ViewPager viewpager;
    private List<String> titleList = new ArrayList<>();//标题的集合
    private List<Fragment> fragmentList = new ArrayList<>();//Fragment的集合
    private MyFragmentPagerAdapter adapter;


    public DeliverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    protected void initView() {
        tablayout = (TabLayout) getActivity().findViewById(R.id.search_tb);
        viewpager = (ViewPager) getActivity().findViewById(R.id.search_vp);

        titleList = new ArrayList<>();
        titleList.add("可接订单");
        titleList.add("已接订单");

        fragmentList = new ArrayList<>();
        fragmentList.add(DeliverOrderFragment.newInstance(0));
        fragmentList.add(DeliverOrderFragment.newInstance(1));

        //设置TabLayout的模式
        tablayout.setTabMode(TabLayout.MODE_FIXED);

        for (String title : titleList) {
            tablayout.addTab(tablayout.newTab().setText(title));
        }

        //一定要使用getChildFragmentManager()否则再次切换无法显示
        adapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList, titleList);
        //viewpager加载adapter
        viewpager.setAdapter(adapter);
        //TabLayout加载viewpager
        tablayout.setupWithViewPager(viewpager);
    }

    public static DeliverFragment newInstance() {
        DeliverFragment fragment = new DeliverFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deliver, container, false);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;                         //fragment列表
        private List<String> titleList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
            super(fm);                  //tab名的列表

            this.fragmentList = fragmentList;
            this.titleList = titleList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        //此方法用来显示tab上的名字
        @Override
        public CharSequence getPageTitle(int position) {

            return titleList.get(position % titleList.size());
        }
    }

    private class MyPagerAdapter extends PagerAdapter {
        private List<View> viewList;

        public MyPagerAdapter(List<View> viewList) {
            this.viewList = viewList;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));//添加页卡
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);//页卡标题
        }
    }

}
