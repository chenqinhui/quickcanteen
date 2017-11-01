package com.quickcanteen.quickcanteen.activities.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.actions.user.IUserAction;
import com.quickcanteen.quickcanteen.actions.user.impl.UserActionImpl;
import com.quickcanteen.quickcanteen.activities.BaseActivity;
import com.quickcanteen.quickcanteen.activities.collect.CollectActivity;
import com.quickcanteen.quickcanteen.activities.settings.SettingsActivity;
import com.quickcanteen.quickcanteen.activities.userinformation.UserInformation;
import com.quickcanteen.quickcanteen.bean.UserInfoBean;
import com.quickcanteen.quickcanteen.fragment.DeliverFragment;
import com.quickcanteen.quickcanteen.fragment.deliverOrder.DeliverOrderFragment;
import com.quickcanteen.quickcanteen.fragment.historyOrder.HistoryOrderFragment;
import com.quickcanteen.quickcanteen.fragment.main.MainFragment;
import com.quickcanteen.quickcanteen.fragment.personal.PersonalFragment;
import com.quickcanteen.quickcanteen.utils.AsyncBitmapLoader;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener, View.OnClickListener {


    private ArrayList<Fragment> fragments;

    protected DrawerLayout drawerLayout;


    protected NavigationView navigationView;

    //@BindView(R.id.head_portrait)
    protected CircleImageView headPortraitCircleImageView;


    protected CircleImageView smallHeadPortraitCircleImageView;

    //@BindView(R.id.nick_name)
    protected TextView nickName;

    //@BindView(R.id.telephone)
    protected TextView telephone;

    private IUserAction userAction = new UserActionImpl(this);

    private UserInfoBean userInfoBean;

    private BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        new Thread(new MyThread()).start();
    }

    @Override
    protected void initView() {
        super.initView();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                headPortraitCircleImageView = (CircleImageView) navigationView.findViewById(R.id.head_portrait);
                nickName = (TextView) navigationView.findViewById(R.id.nick_name);
                telephone = (TextView) navigationView.findViewById(R.id.telephone);
                nickName.setText(userInfoBean.getNickName());
                telephone.setText(userInfoBean.getTelephone());
                Bitmap bitmap = asyncBitmapLoader.loadBitmap(headPortraitCircleImageView, userInfoBean.getHeadPortrait(), headPortraitCircleImageView.getLayoutParams().width, headPortraitCircleImageView.getLayoutParams().height, new AsyncBitmapLoader.ImageCallBack() {
                    @Override
                    public void imageLoad(ImageView imageView, Bitmap bitmap) {
                        // TODO Auto-generated method stub
                        imageView.setImageBitmap(bitmap);
                        //item.picture = bitmap;
                    }
                });
                if (bitmap != null) {
                    headPortraitCircleImageView.setImageBitmap(bitmap);
                }
            }
        });
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        ViewGroup.LayoutParams params = navigationView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels * 2 / 3;
        navigationView.setLayoutParams(params);
        //navigationView.setItemTextColor(R.color.white);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = new Intent();
                switch (item.getItemId()) {
                    case R.id.personal:
                        intent.setClass(MainActivity.this, UserInformation.class);
                        intent.putExtra("userID", userAction.getCurrentUserID());
                        startActivity(intent);
                        break;
                    case R.id.nav_collect:
                        intent.setClass(MainActivity.this, CollectActivity.class);
                        intent.putExtra("userID", userAction.getCurrentUserID());
                        intent.putExtra("title", "我的收藏");
                        startActivity(intent);
                        break;
                    case R.id.nav_settings:
                        intent.setClass(MainActivity.this, SettingsActivity.class);
                        //intent.putExtra("userID", baseAction.getCurrentUserID());
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
        smallHeadPortraitCircleImageView = (CircleImageView) findViewById(R.id.toolBar_head_portrait);
        smallHeadPortraitCircleImageView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolBar_head_portrait:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.layFrame, MainFragment.newInstance());
        transaction.commitAllowingStateLoss();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(MainFragment.newInstance());
        fragments.add(HistoryOrderFragment.newInstance());
        fragments.add(PersonalFragment.newInstance());
        fragments.add(DeliverFragment.newInstance());
        return fragments;
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                switch (position) {
                    case 0:
                        setToolBarTitleText("主页");
                        fragment = MainFragment.newInstance();
                        break;
                    case 1:
                        setToolBarTitleText("历史订单");
                        fragment = HistoryOrderFragment.newInstance();
                        break;
                    case 2:
                        setToolBarTitleText("个人信息");
                        fragment = PersonalFragment.newInstance();
                        break;
                    case 3:
                        setToolBarTitleText("配送");
                        fragment = DeliverFragment.newInstance();
                        break;
                    default:
                        break;
                }
                fragments.remove(position);
                fragments.add(position, fragment);
                if (fragment.isAdded()) {
                    ft.replace(R.id.layFrame, fragment);
                } else {
                    ft.add(R.id.layFrame, fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }

    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    public class MyThread implements Runnable {
        @Override
        public void run() {
            bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
            bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
            bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
            BadgeItem numberBadgeItem = new BadgeItem()
                    .setBorderWidth(4)
                    .setBackgroundColor(Color.RED)
                    .setText("3")
                    .setHideOnSelect(true);
            bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.bottom_main, "主页").setActiveColorResource(R.color.colorPrimary))
                    .addItem(new BottomNavigationItem(R.drawable.bottom_order, "历史订单").setActiveColorResource(R.color.colorPrimary))
                    .addItem(new BottomNavigationItem(R.drawable.bottom_my, "个人信息").setActiveColorResource(R.color.colorPrimary));
            try {
                BaseJson baseJson = userAction.getCurrentUserInfo();

                userInfoBean = new UserInfoBean(baseJson.getJSONObject());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap1 = asyncBitmapLoader.loadBitmap(smallHeadPortraitCircleImageView, userInfoBean.getHeadPortrait(), smallHeadPortraitCircleImageView.getLayoutParams().width, smallHeadPortraitCircleImageView.getLayoutParams().height, new AsyncBitmapLoader.ImageCallBack() {
                            @Override
                            public void imageLoad(ImageView imageView, Bitmap bitmap) {
                                // TODO Auto-generated method stub
                                imageView.setImageBitmap(bitmap);
                                //item.picture = bitmap;
                            }
                        });
                        if (bitmap1 != null) {
                            smallHeadPortraitCircleImageView.setImageBitmap(bitmap1);
                        }
                        if (userInfoBean.getDeliver()) {
                            bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.bottom_deliver, "配送").setActiveColorResource(R.color.colorPrimary));
                        }

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bottomNavigationBar.setFirstSelectedPosition(0).initialise();
                    fragments = getFragments();
                    //setDefaultFragment();
                    bottomNavigationBar.setTabSelectedListener(MainActivity.this);
                    onTabSelected(0);

                }
            });
        }
    }
}
