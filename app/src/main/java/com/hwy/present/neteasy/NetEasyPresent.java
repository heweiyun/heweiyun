package com.hwy.present.neteasy;

import com.hwy.data.model.NetEasyNewsItem;
import com.hwy.data.net.response.NetEasyNewsListResp;
import com.hwy.data.net.service.NetEasyService;
import com.hwy.data.rx.ResponseObserver;
import com.hwy.present.base.BasePresent;
import com.hwy.ui.module.neteasy.INetEasyView;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/30 11:37
 * 修改人：heweiyun
 * 修改时间：2016/11/30 11:37
 * 修改备注：
 */

public class NetEasyPresent extends BasePresent<INetEasyView> {

    private NetEasyService mNetEasyService;

    private int mPageIndex = 0;

    @Inject
    public NetEasyPresent(NetEasyService netEasyService) {
        this.mNetEasyService = netEasyService;
    }

    public void getNetEasyList() {
        mCompositeSubscription.add(mNetEasyService.getNews(mPageIndex)
                .map(new Func1<NetEasyNewsListResp, List<NetEasyNewsItem>>() {
                    @Override
                    public List<NetEasyNewsItem> call(NetEasyNewsListResp netEasyNewsListResp) {
                        if (null != netEasyNewsListResp.newsList){
                            netEasyNewsListResp.newsList.remove(0);
                        }
                        return netEasyNewsListResp.newsList;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (mPageIndex == 0){
                            mView.showLoadingContent();
                        }
                    }
                })
                .subscribe(new ResponseObserver<List<NetEasyNewsItem>>() {

                    @Override
                    public void onSuccess(List<NetEasyNewsItem> dataList) {
                        if (mPageIndex == 0){
                            if (null == dataList || dataList.size() < 1){
                                mView.showContentEmpty();
                            }else {
                                mView.showContent(dataList);
                            }
                        }else {
                            if (null == dataList || dataList.size() < 1){
                                mView.showLoadNomore();
                            }else {
                                mView.showMoreContent(dataList);
                            }
                        }
                        mPageIndex += 20;
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mPageIndex == 0){
                            mView.showLoadContentError();
                        }else {
                            mView.showLoadingMoreError();
                        }
                    }
                }));
    }

}
