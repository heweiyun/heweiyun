package com.hwy.ui.module.neteasy;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hwy.R;
import com.hwy.data.model.NetEasyNewsItem;
import com.hwy.di.component.MainComponent;
import com.hwy.present.neteasy.NetEasyPresent;
import com.hwy.ui.base.BaseFragment;
import com.hwy.ui.widget.LoadView;
import com.hwy.ui.widget.ReloadListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

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

public class NetEasyFragment extends BaseFragment implements INetEasyView{
    private static final String TAG = NetEasyFragment.class.getSimpleName();

    @BindView(R.id.neteasy_rv)
    RecyclerView mRecyclerView;

    private NetEasyAdapter mNetEasyAdapter;

    @BindView(R.id.neteasy_loadview)
    LoadView mLoadView;

    @Inject
    NetEasyPresent mNetEasyPresent;



    @Override
    protected int getContentResId() {
        return R.layout.fragment_neteasy;
    }

    @Override
    protected void injectDagger() {
        getComponent(MainComponent.class).inject(this);
    }


    @Override
    protected void initViews(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(Color.TRANSPARENT)
                .size(10)
                .build());
    }

    @Override
    protected void initDatas() {
        mNetEasyAdapter = new NetEasyAdapter(null);
        mNetEasyAdapter.setEnableLoadMore(true);
        mNetEasyAdapter.setAutoLoadMoreSize(2);
        mNetEasyAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mNetEasyAdapter);

        mNetEasyPresent.getNetEasyList();
    }

    @Override
    protected void initEvents() {
        mLoadView.setReloadListener(new ReloadListener() {
            @Override
            public void reload() {
                mNetEasyPresent.getNetEasyList();
            }
        });
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                NetEasyDetailActivity.geNetEasyDetail(getActivity(),(NetEasyNewsItem) baseQuickAdapter.getItem(i),view.findViewById(R.id.item_neteasy_imv_icon));
            }
        });
        mNetEasyAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mNetEasyPresent.getNetEasyList();
            }
        });
    }

    @Override
    protected void attatchPresent() {
        mNetEasyPresent.attachView(this);
    }

    @Override
    protected void detachPresent() {
        mNetEasyPresent.detachView();
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
        mNetEasyAdapter.loadMoreFail();
    }

    @Override
    public void onLoadSuccess(List<NetEasyNewsItem> dataList) {
        mLoadView.setLoadSuccessMode();
        mNetEasyAdapter.addData(dataList);
    }

    @Override
    public void onLoadMore(List<NetEasyNewsItem> dataList) {
        mNetEasyAdapter.loadMoreComplete();
        mNetEasyAdapter.addData(dataList);
    }

    @Override
    public void onLoadNomore() {
        mNetEasyAdapter.loadMoreEnd();
    }

    @Override
    public void onLoadEmpty() {
        mLoadView.setLoadSuccessMode();
        mNetEasyAdapter.setEmptyView(getEmptyView());
    }

    private View getEmptyView(){
        return LayoutInflater.from(getActivity()).inflate(R.layout.view_zhihu_empty,mRecyclerView,false);
    }
}
