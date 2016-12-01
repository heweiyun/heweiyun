package com.hwy.ui.module.neteasy;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.hwy.HwyApplication;
import com.hwy.R;
import com.hwy.common.wrapper.ImageLoader;
import com.hwy.data.model.NetEasyNewsItem;
import com.hwy.data.net.response.NetEasyNewsDetailResp;
import com.hwy.di.component.DaggerMainComponent;
import com.hwy.di.module.ActivityModule;
import com.hwy.present.neteasy.NetEasyDetailPresent;
import com.hwy.ui.base.BaseActivity;
import com.hwy.ui.widget.AppBarStateChangeListener;
import com.hwy.ui.widget.LoadView;
import com.hwy.ui.widget.ReloadListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/30 17:34
 * 修改人：heweiyun
 * 修改时间：2016/11/30 17:34
 * 修改备注：
 */

public class NetEasyDetailActivity extends BaseActivity implements INetEasyDetailView {
    private static final String TAG = NetEasyDetailActivity.class.getSimpleName();

    private static final String INTENT_DATA_ID = "intent_data_id";
    private static final String INTENT_DATA_TITLE = "intent_data_title";
    private static final String INTENT_DATA_IMAGE = "intent_data_imageurl";


    @Inject
    NetEasyDetailPresent mNetEasyDetailPresent;


    private String mId;
    private String mTitle;
    private String mImageUrl;

    private boolean mIsCoverVisiable = true;

    @BindView(R.id.neteasy_desc_imv_cover)
    ImageView mImvCover;

    @BindView(R.id.neteasy_desc_loadview)
    LoadView mLoadView;

    @BindView(R.id.neteasy_desc_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.neteasy_desc_appbar)
    AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neteasy_desc);
        ButterKnife.bind(this);
        DaggerMainComponent.builder().applicationComponent(HwyApplication.get(this).getApplicationComponent())
                .activityModule(new ActivityModule(this)).build().inject(this);
        mNetEasyDetailPresent.attachView(this);
        initView();
        initData();
        bindEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNetEasyDetailPresent.detachView();
    }


    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mIsCoverVisiable) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    private void initView(){}

    private void initData(){
        mId = getIntent().getStringExtra(INTENT_DATA_ID);
        mTitle = getIntent().getStringExtra(INTENT_DATA_TITLE);
        mImageUrl = getIntent().getStringExtra(INTENT_DATA_IMAGE);
        mNetEasyDetailPresent.getNetEasyDetail(mId);
        ImageLoader.load(mImageUrl, mImvCover);

        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);
    }

    private void bindEvent(){
        mLoadView.setReloadListener(new ReloadListener() {
            @Override
            public void reload() {
                mNetEasyDetailPresent.getNetEasyDetail(mId);
            }
        });

        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED){
                    mIsCoverVisiable = true;
                }else {
                    mIsCoverVisiable = false;
                }
            }
        });
    }

    @Override
    public void showLoadingContent() {
        mLoadView.setLoadingMode();
    }

    @Override
    public void showLoadContentError() {
        mLoadView.setLoadFailedMode();
    }

    @Override
    public void showContent(NetEasyNewsDetailResp detailResp) {
        mLoadView.setLoadSuccessMode();

    }

    public static void geNetEasyDetail(Activity activity, NetEasyNewsItem item, View cover){
        Intent intent = new Intent(activity, NetEasyDetailActivity.class);
        intent.putExtra(INTENT_DATA_ID, item.docid);
        intent.putExtra(INTENT_DATA_IMAGE, item.imgsrc);
        intent.putExtra(INTENT_DATA_TITLE, item.title);
        ActivityOptions options = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation(activity, cover, activity.getResources().getString
                    (R.string.neteasy_cover_transition_name));
            activity.startActivity(intent, options.toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

}
