package com.hwy.ui.module.zhihu;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.hwy.HwyApplication;
import com.hwy.R;
import com.hwy.common.util.WebUtil;
import com.hwy.common.wrapper.ImageLoader;
import com.hwy.data.model.ZhihuDailyItem;
import com.hwy.data.model.ZhihuStory;
import com.hwy.di.component.DaggerZhiHuDescribleComponent;
import com.hwy.di.component.ZhiHuDescribleComponent;
import com.hwy.present.zhihu.ZhiHuDescriblePresent;
import com.hwy.ui.base.BaseActivity;
import com.hwy.ui.widget.AppBarStateChangeListener;
import com.hwy.ui.widget.LoadView;
import com.hwy.ui.widget.ReloadListener;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/29 17:11
 * 修改人：heweiyun
 * 修改时间：2016/11/29 17:11
 * 修改备注：
 */

public class ZhiHuDescribleActivity extends BaseActivity<ZhiHuDescribleComponent> implements IZhiHuDescribleView {
    private static final String TAG = ZhiHuDescribleActivity.class.getSimpleName();

    private static final String INTENT_DATA_ID = "intent_data_id";
    private static final String INTENT_DATA_TITLE = "intent_data_title";
    private static final String INTENT_DATA_IMAGE = "intent_data_imageurl";

    @BindView(R.id.zhihu_desc_webview)
    WebView mWebView;

    private boolean mIsCoverVisiable = true;

    @BindView(R.id.zhihu_desc_imv_cover)
    ImageView mImvCover;

    @BindView(R.id.zhihu_desc_loadview)
    LoadView mLoadView;

    @BindView(R.id.zhihu_desc_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.zhihu_desc_appbar)
    AppBarLayout mAppBarLayout;


    @Inject
    ZhiHuDescriblePresent mZhiHuDescriblePresent;

    private String mId;
    private String mTitle;
    private String mImageUrl;


    @Override
    protected int getContentResId() {
        return R.layout.activity_zhihu_desc;
    }

    @Override
    protected void injectDagger() {
        getComponent().inject(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            mWebView.getClass().getMethod("onResume").invoke(mWebView, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mWebView.getClass().getMethod("onPause").invoke(mWebView, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //webview内存泄露
        if (mWebView != null) {
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mIsCoverVisiable) {
            finishAfterTransition();
            mWebView.setVisibility(View.GONE);
        } else {
            finish();
        }
    }


    @Override
    protected void initViews() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    protected void initDatas() {
        mId = getIntent().getStringExtra(INTENT_DATA_ID);
        mTitle = getIntent().getStringExtra(INTENT_DATA_TITLE);
        mImageUrl = getIntent().getStringExtra(INTENT_DATA_IMAGE);
        mZhiHuDescriblePresent.getZhiHuStory(mId);
        ImageLoader.load(mImageUrl, mImvCover);

        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void initEvents() {
        mLoadView.setReloadListener(new ReloadListener() {
            @Override
            public void reload() {
                mZhiHuDescriblePresent.getZhiHuStory(mId);
            }
        });

        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    mIsCoverVisiable = true;
                } else {
                    mIsCoverVisiable = false;
                }
            }
        });
    }

    @Override
    protected void attatchPresent() {
        mZhiHuDescriblePresent.attachView(this);
    }

    @Override
    protected void detachPresent() {
        mZhiHuDescriblePresent.detachView();
    }

    public static void goDescrible(Activity activity, ZhihuDailyItem item, View cover) {
        Intent intent = new Intent(activity, ZhiHuDescribleActivity.class);
        intent.putExtra(INTENT_DATA_ID, item.id);
        intent.putExtra(INTENT_DATA_IMAGE, item.images[0]);
        intent.putExtra(INTENT_DATA_TITLE, item.title);
        ActivityOptions options = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation(activity, cover, activity.getResources().getString
                    (R.string.zhihu_cover_transition_name));
            activity.startActivity(intent, options.toBundle());
        } else {
            activity.startActivity(intent);
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
    public void onLoadSuccess(ZhihuStory story) {
        mLoadView.setLoadSuccessMode();
        mWebView.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(story.body)) {
            mWebView.loadUrl(story.share_url);
        } else {
            String data = WebUtil.buildHtmlWithCss(story.body, story.css, false);
            mWebView.loadDataWithBaseURL(WebUtil.BASE_URL, data, WebUtil.MIME_TYPE, WebUtil.ENCODING, WebUtil.FAIL_URL);
        }
    }

    @Override
    public void onLoadEmpty() {

    }

    @Override
    public ZhiHuDescribleComponent getComponent() {
        return  DaggerZhiHuDescribleComponent
                .builder()
                .applicationComponent(HwyApplication.get(this).getApplicationComponent())
                .build();
    }
}
