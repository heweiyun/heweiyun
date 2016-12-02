package com.hwy.ui.module.neteasy;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.hwy.HwyApplication;
import com.hwy.R;
import com.hwy.common.wrapper.ImageLoader;
import com.hwy.data.model.NetEasyNewsItem;
import com.hwy.data.net.response.NetEasyNewsDetailResp;
import com.hwy.di.component.DaggerNetEasyDetailComponent;
import com.hwy.di.component.NetEasyDetailComponent;
import com.hwy.present.neteasy.NetEasyDetailPresent;
import com.hwy.ui.base.BaseActivity;
import com.hwy.ui.widget.AppBarStateChangeListener;
import com.hwy.ui.widget.LoadView;
import com.hwy.ui.widget.ReloadListener;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/30 17:34
 * 修改人：heweiyun
 * 修改时间：2016/11/30 17:34
 * 修改备注：
 */

public class NetEasyDetailActivity extends BaseActivity<NetEasyDetailComponent> implements INetEasyDetailView {
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

    @BindView(R.id.neteasy_html)
    HtmlTextView mHtmlTextView;


    @Override
    protected int getContentResId() {
        return R.layout.activity_neteasy_desc;
    }

    @Override
    protected void injectDagger() {
        getComponent().inject(this);
    }


    @Override
    protected void initViews() {
    }

    @Override
    protected void initDatas() {
        mId = getIntent().getStringExtra(INTENT_DATA_ID);
        mTitle = getIntent().getStringExtra(INTENT_DATA_TITLE);
        mImageUrl = getIntent().getStringExtra(INTENT_DATA_IMAGE);
        mNetEasyDetailPresent.getNetEasyDetail(mId);
        ImageLoader.load(mImageUrl, mImvCover);

        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void initEvents() {
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
    protected void attatchPresent() {
        mNetEasyDetailPresent.attachView(this);
    }

    @Override
    protected void detachPresent() {
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




    @Override
    public void onLoading() {
        mLoadView.setLoadingMode();
    }

    @Override
    public void onLoadError() {
        mLoadView.setLoadFailedMode();
    }

    @Override
    public void onLoadSuccess(NetEasyNewsDetailResp detailResp) {
        mLoadView.setLoadSuccessMode();
        mHtmlTextView.setHtmlFromString(detailResp.body, new HtmlTextView.LocalImageGetter());
    }

    @Override
    public void onLoadEmpty() {

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

    @Override
    public NetEasyDetailComponent getComponent() {
        return DaggerNetEasyDetailComponent
                .builder()
                .applicationComponent(HwyApplication.get(this).getApplicationComponent())
                .build();
    }
}
