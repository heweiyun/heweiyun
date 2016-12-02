package com.hwy.ui.module.main;

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
import com.roughike.bottombar.OnTabSelectListener;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainComponent>{
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.main_bottombar)
    BottomBar mBottomBar;

    @Inject
    NetEasyDetailPresent mNetEasyDetailPresent;


    private FragmentManager mFragmentManager = getSupportFragmentManager();



    @Override
    protected int getContentResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void injectDagger() {

    }

    protected void initViews(){

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initEvents() {
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switchMenu(getFragmentName(tabId));
            }
        });
    }

    @Override
    protected void attatchPresent() {

    }

    @Override
    protected void detachPresent() {

    }



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
