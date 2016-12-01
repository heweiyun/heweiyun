package com.hwy.ui.module.zhihu;

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
import com.hwy.data.model.ZhihuDailyItem;
import com.hwy.data.net.response.ZhihuDailyResp;
import com.hwy.di.component.MainComponent;
import com.hwy.present.zhihu.ZhiHuPresent;
import com.hwy.ui.base.BaseFragment;
import com.hwy.ui.widget.LoadView;
import com.hwy.ui.widget.ReloadListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

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

public class ZhiHuFragment extends BaseFragment implements IZhiHuView{
    private static final String TAG = ZhiHuFragment.class.getSimpleName();

    @Inject
    ZhiHuPresent mZhiHuPresent;

    @BindView(R.id.zhihu_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.zhihu_loadview)
    LoadView mLoadView;

    private ZhiHuAdapter mZhiHuAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(MainComponent.class).inject(this);
        mZhiHuPresent.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mZhiHuPresent.detachView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_zhihu,null);
        ButterKnife.bind(this,contentView);
        initViews();
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mZhiHuPresent.getLatestZhiHuNews();
    }

    private void initViews(){
        mZhiHuAdapter = new ZhiHuAdapter(null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(Color.TRANSPARENT)
                .size(10)
                .build());
        mRecyclerView.setAdapter(mZhiHuAdapter);

        mLoadView.setReloadListener(new ReloadListener() {
            @Override
            public void reload() {
                mZhiHuPresent.getLatestZhiHuNews();
            }
        });

        mZhiHuAdapter.setEnableLoadMore(true);
        mZhiHuAdapter.setAutoLoadMoreSize(2);
        mZhiHuAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mZhiHuPresent.getTheDaily();
            }
        });


        mZhiHuAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);


        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ZhihuDailyItem zhihuDailyItem = (ZhihuDailyItem) baseQuickAdapter.getItem(i);
                ZhiHuDescribleActivity.goDescrible(getActivity(),zhihuDailyItem,view.findViewById(R.id.item_zhihu_imv_icon));
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
    public void showLoadingMoreError() {
        mZhiHuAdapter.loadMoreFail();
    }

    @Override
    public void showContent(ZhihuDailyResp resp) {
        mLoadView.setLoadSuccessMode();
        mZhiHuAdapter.addData(resp.stories);
    }

    @Override
    public void showMoreContent(ZhihuDailyResp resp) {
        mZhiHuAdapter.loadMoreComplete();
        mZhiHuAdapter.addData(resp.stories);
    }

    @Override
    public void showLoadNomore() {
        mZhiHuAdapter.loadMoreEnd();
    }

    @Override
    public void showContentEmpty() {
        mLoadView.setLoadSuccessMode();
        mZhiHuAdapter.setEmptyView(getEmptyView());
    }

    private View getEmptyView(){
        return LayoutInflater.from(getActivity()).inflate(R.layout.view_zhihu_empty,mRecyclerView,false);
    }
}
