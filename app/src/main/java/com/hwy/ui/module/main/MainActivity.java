package com.hwy.ui.module.main;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.hwy.HwyApplication;
import com.hwy.R;
import com.hwy.di.component.DaggerMainComponent;
import com.hwy.di.component.MainComponent;
import com.hwy.di.module.ActivityModule;
import com.hwy.present.neteasy.NetEasyDetailPresent;
import com.hwy.ui.base.BaseActivity;
import com.hwy.ui.module.neteasy.NetEasyFragment;
import com.hwy.ui.module.zhihu.ZhiHuFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainComponent>{
    private static final String TAG = MainActivity.class.getSimpleName();

    private BottomBar mBottomBar;

    @Inject
    NetEasyDetailPresent mNetEasyDetailPresent;


    private FragmentManager mFragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mBottomBar = BottomBar.attach(this,savedInstanceState);
//        mBottomBar.setItems(R.menu.main_bottom_menu);
//        mBottomBar.setOnMenuTabClickListener(mTabClickListener);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void injectDagger() {

    }

    protected void initViews(){
        mBottomBar.setItems(R.menu.main_bottom_menu);
        mBottomBar.setOnMenuTabClickListener(mTabClickListener);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void attatchPresent() {

    }

    @Override
    protected void detachPresent() {

    }

    private OnMenuTabClickListener mTabClickListener = new OnMenuTabClickListener() {
        @Override
        public void onMenuTabSelected(@IdRes int menuItemId) {
            switchMenu(getFragmentName(menuItemId));
        }

        @Override
        public void onMenuTabReSelected(@IdRes int menuItemId) {

        }
    };

    private Fragment mCurrentFragment;
    private void switchMenu(String fragmentName){
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentName);
        if (null != fragment){
            if (mCurrentFragment == fragment){
                return;
            }else {
                mFragmentManager.beginTransaction().show(fragment).commit();
            }
        }else {
            fragment = Fragment.instantiate(this,fragmentName);
            mFragmentManager.beginTransaction().add(R.id.main_content_frame,fragment,fragmentName).commit();
        }

        if (null != mCurrentFragment){
            mFragmentManager.beginTransaction().hide(mCurrentFragment).commit();
        }

        mCurrentFragment = fragment;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mBottomBar.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    private String getFragmentName(int menuId){
        switch (menuId){
            case R.id.main_bottom_neteasy:
                return NetEasyFragment.class.getName();
            case R.id.main_bottom_zhihu:
                return ZhiHuFragment.class.getName();
            default:
                return null;
        }
    }

    @Override
    public MainComponent getComponent() {
        return DaggerMainComponent.builder()
                .applicationComponent(HwyApplication.get(this).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }
}
