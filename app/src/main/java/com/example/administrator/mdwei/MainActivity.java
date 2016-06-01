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
import android.widget.Toast;

import com.example.administrator.mdwei.model.goodfriend.GoodFriendFragment;
import com.example.administrator.mdwei.model.hotblog.HotBlogFragment;
import com.example.administrator.mdwei.model.topic.TopicFragment;
import com.example.administrator.mdwei.service.UsersService;
import com.example.administrator.mdwei.util.URLConstant;
import com.example.administrator.mdwei.util.bean.AccessTokenKeeper;
import com.google.gson.JsonObject;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //如果Token为空则跳转到授权登陆界面
      /*  Oauth2AccessToken mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (!mAccessToken.isSessionValid()) {
            Intent intent = new Intent(MainActivity.this, WBAuthActivity.class);
            startActivity(intent);
            return;
        }*/

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

        mMianViewpage = (ViewPager) findViewById(R.id.main_viewpage);
        mMianViewpage.setOffscreenPageLimit(3);


        initView();
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

        //   getUsersShow();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
                .subscribe(new Subscriber<JsonObject>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("LOG", "" + e);
                    }

                    @Override
                    public void onNext(JsonObject movieEntity) {
                        Log.i("LOG", "" + movieEntity);
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


}
