package com.hwy.ui.module.neteasy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import butterknife.ButterKnife;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(MainComponent.class).inject(this);
        mNetEasyPresent.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNetEasyPresent.detachView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_neteasy,null);
        ButterKnife.bind(this,contentView);
        initViews();
        return contentView;
    }

    private void initViews(){
        mNetEasyAdapter = new NetEasyAdapter(null);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(Color.TRANSPARENT)
                .size(10)
                .build());
        mRecyclerView.setAdapter(mNetEasyAdapter);

        mLoadView.setReloadListener(new ReloadListener() {
            @Override
            public void reload() {
                mNetEasyPresent.getNetEasyList();
            }
        });

        mNetEasyAdapter.setEnableLoadMore(true);
        mNetEasyAdapter.setAutoLoadMoreSize(2);
        mNetEasyAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mNetEasyPresent.getNetEasyList();
            }
        });

        mNetEasyAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                NetEasyDetailActivity.geNetEasyDetail(getActivity(),(NetEasyNewsItem) baseQuickAdapter.getItem(i),view.findViewById(R.id.item_neteasy_imv_icon));
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNetEasyPresent.getNetEasyList();
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
    public void showLoadingMoreError() {
        mNetEasyAdapter.loadMoreFail();
    }

    @Override
    public void showContent(List<NetEasyNewsItem> dataList) {
        mLoadView.setLoadSuccessMode();
        mNetEasyAdapter.addData(dataList);
    }

    @Override
    public void showMoreContent(List<NetEasyNewsItem> dataList) {
        mNetEasyAdapter.loadMoreComplete();
        mNetEasyAdapter.addData(dataList);
    }

    @Override
    public void showLoadNomore() {
        mNetEasyAdapter.loadMoreEnd();
    }

    @Override
    public void showContentEmpty() {
        mLoadView.setLoadSuccessMode();
        mNetEasyAdapter.setEmptyView(getEmptyView());
    }

    private View getEmptyView(){
        return LayoutInflater.from(getActivity()).inflate(R.layout.view_zhihu_empty,mRecyclerView,false);
    }
}
