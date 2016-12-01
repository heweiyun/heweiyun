package com.hwy.present.zhihu;

import com.hwy.data.model.ZhihuDailyItem;
import com.hwy.data.net.response.ZhihuDailyResp;
import com.hwy.data.net.service.ZhiHuService;
import com.hwy.data.rx.ResponseObserver;
import com.hwy.present.base.BasePresent;
import com.hwy.ui.module.zhihu.IZhiHuView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/28 16:43
 * 修改人：heweiyun
 * 修改时间：2016/11/28 16:43
 * 修改备注：
 */

public class ZhiHuPresent extends BasePresent<IZhiHuView> {

    private ZhiHuService mZhiHuService;

    private String mCurDate = "0";


    @Inject
    public ZhiHuPresent(ZhiHuService zhiHuService) {
        this.mZhiHuService = zhiHuService;
    }


    /**
     * 获取第一页的内容
     */
    public void getLatestZhiHuNews() {
        mCompositeSubscription.add(mZhiHuService.getLastDaily()
                .map(new Func1<ZhihuDailyResp, ZhihuDailyResp>() {
                    @Override
                    public ZhihuDailyResp call(ZhihuDailyResp resp) {
                        if (null == resp.stories){
                            return resp;
                        }
                        String date = resp.date;
                        for (ZhihuDailyItem item : resp.stories) {
                            item.date = date;
                        }
                        return resp;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoadingContent();
                    }
                })
                .subscribe(new ResponseObserver<ZhihuDailyResp>() {

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoadContentError();
                    }

                    @Override
                    public void onSuccess(ZhihuDailyResp resp) {
                        mCurDate = resp.date;
                        if (null == resp.stories || resp.stories.size() < 1){
                            mView.showContentEmpty();
                        }else {
                            mView.showContent(resp);
                        }
                    }
                }));
    }

    /**
     * 获取更多页的内容
     *
     */
    public void getTheDaily() {
        mCompositeSubscription.add(mZhiHuService.getTheDaily(mCurDate)
                .map(new Func1<ZhihuDailyResp, ZhihuDailyResp>() {
                    @Override
                    public ZhihuDailyResp call(ZhihuDailyResp resp) {
                        if (null == resp.stories || resp.stories.size() < 1){
                            return resp;
                        }else {
                            String date = resp.date;
                            for (ZhihuDailyItem item : resp.stories) {
                                item.date = date;
                            }
                            return resp;
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<ZhihuDailyResp>() {
                    @Override
                    public void onSuccess(ZhihuDailyResp resp) {
                        mCurDate = resp.date;
                        if (null == resp.stories || resp.stories.size() < 1){
                            mView.showLoadNomore();
                        }else {
                            mView.showMoreContent(resp);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoadingMoreError();
                    }
                }));
    }

}
