package com.example.administrator.mdwei;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.mdwei.bean.Users;
import com.example.administrator.mdwei.modular.goodfriend.GoodFriendFragment;
import com.example.administrator.mdwei.modular.hotblog.HotBlogFragment;
import com.example.administrator.mdwei.modular.topic.TopicFragment;
import com.example.administrator.mdwei.service.UsersService;
import com.example.administrator.mdwei.util.URLConstant;
import com.example.administrator.mdwei.util.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager mMianViewpage;
    private TabLayout mTabLayout;
    private ImageView iv_head_icon;

    private Users mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        iv_head_icon = (ImageView) view.findViewById(R.id.iv_head_icon);


        mMianViewpage = (ViewPager) findViewById(R.id.main_viewpage);
        mMianViewpage.setOffscreenPageLimit(3);
        //如果Token为空则跳转到授权登陆界面
        Oauth2AccessToken mAccessToken = AccessTokenKeeper.readAccessToken(this);

        initView();
        if (!mAccessToken.isSessionValid()) {
            Intent intent = new Intent(MainActivity.this, WBAuthActivity.class);
            startActivity(intent);
            return;
        } else {
            getUsersShow();
        }
    }


    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        List<String> mTitleList = new ArrayList<>();
        mTitleList.add("看朋友");
        mTitleList.add("热门微博");
        mTitleList.add("热门话题");
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new GoodFriendFragment());
        fragments.add(new HotBlogFragment());
        fragments.add(new TopicFragment());
        PageAdapter adapter =
                new PageAdapter(getSupportFragmentManager(), fragments, mTitleList);
        mMianViewpage.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mMianViewpage);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //当导航项被点击时的回调
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //进行网络请求
    private void getUsersShow() {
        Oauth2AccessToken mAccessToken = AccessTokenKeeper.readAccessToken(this);
        String token = mAccessToken.getToken();
        long uid = Long.valueOf(mAccessToken.getUid());

        String baseUrl = URLConstant.BaseUrl;

        Log.i("LOG", "**" + token + "**" + uid + "---" + baseUrl);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        UsersService movieService = retrofit.create(UsersService.class);

        movieService.getUsersShow(token, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Users>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("LOG", "" + e);
                    }

                    @Override
                    public void onNext(Users movieEntity) {
                        Glide.with(MainActivity.this)
                                .load(movieEntity.getProfile_image_url())
                                .into(iv_head_icon);

                    }
                });
    }


    public class PageAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;
        private List<String> mTitles;

        public PageAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            this.mFragments = fragments;
            this.mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
    }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
