package com.hwy.ui.module.zhihu;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hwy.R;
import com.hwy.data.model.ZhihuDailyItem;
import com.hwy.data.net.response.ZhihuDailyResp;
import com.hwy.di.component.MainComponent;
import com.hwy.present.zhihu.ZhiHuPresent;
import com.hwy.ui.base.BaseFragment;
import com.hwy.ui.widget.LoadView;
import com.hwy.ui.widget.ReloadListener;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/28 15:57
 * 修改人：heweiyun
 * 修改时间：2016/11/28 15:57
 * 修改备注：
 */

public class ZhiHuFragment extends BaseFragment implements IZhiHuView{
    private static final String TAG = ZhiHuFragment.class.getSimpleName();

    @Inject
    ZhiHuPresent mZhiHuPresent;

    @Inject
    RxPermissions mRxPermissions;

    @BindView(R.id.zhihu_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.zhihu_loadview)
    LoadView mLoadView;

    private ZhiHuAdapter mZhiHuAdapter;


    @Override
    protected int getContentResId() {
        return R.layout.fragment_zhihu;
    }

    @Override
    protected void injectDagger() {
        getComponent(MainComponent.class).inject(this);
    }


    @Override
    protected  void initViews(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(Color.TRANSPARENT)
                .size(10)
                .build());
    }

    @Override
    protected void initDatas() {
        mZhiHuAdapter = new ZhiHuAdapter(null);
        mZhiHuAdapter.setEnableLoadMore(true);
        mZhiHuAdapter.setAutoLoadMoreSize(2);
        mZhiHuAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mZhiHuAdapter);

        mZhiHuPresent.getLatestZhiHuNews();
    }

    @Override
    protected void initEvents() {
        mZhiHuAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mZhiHuPresent.getTheDaily();
            }
        });
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ZhihuDailyItem zhihuDailyItem = (ZhihuDailyItem) baseQuickAdapter.getItem(i);
                ZhiHuDescribleActivity.goDescrible(getActivity(),zhihuDailyItem,view.findViewById(R.id.item_zhihu_imv_icon));
            }
        });
        mLoadView.setReloadListener(new ReloadListener() {
            @Override
            public void reload() {
                mZhiHuPresent.getLatestZhiHuNews();
            }
        });
    }

    @Override
    protected void attatchPresent() {
        mZhiHuPresent.attachView(this);
    }

    @Override
    protected void detachPresent() {
        mZhiHuPresent.detachView();
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
    public void onLoadMoreError() {
        mZhiHuAdapter.loadMoreFail();
    }

    @Override
    public void onLoadSuccess(ZhihuDailyResp resp) {
        mLoadView.setLoadSuccessMode();
        mZhiHuAdapter.addData(resp.stories);
    }

    @Override
    public void onLoadMore(ZhihuDailyResp resp) {
        mZhiHuAdapter.loadMoreComplete();
        mZhiHuAdapter.addData(resp.stories);
    }

    @Override
    public void onLoadNomore() {
        mZhiHuAdapter.loadMoreEnd();
    }

    @Override
    public void onLoadEmpty() {
        mLoadView.setLoadSuccessMode();
        mZhiHuAdapter.setEmptyView(getEmptyView());
    }

    private View getEmptyView(){
        return LayoutInflater.from(getActivity()).inflate(R.layout.view_zhihu_empty,mRecyclerView,false);
    }
}
