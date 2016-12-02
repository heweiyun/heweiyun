package com.hwy.present.zhihu;

import com.hwy.data.model.ZhihuStory;
import com.hwy.data.net.service.ZhiHuService;
import com.hwy.data.rx.ResponseObserver;
import com.hwy.present.base.BasePresent;
import com.hwy.ui.module.zhihu.IZhiHuDescribleView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/29 17:33
 * 修改人：heweiyun
 * 修改时间：2016/11/29 17:33
 * 修改备注：
 */

public class ZhiHuDescriblePresent extends BasePresent<IZhiHuDescribleView> {

    private ZhiHuService mZhiHuService;

    @Inject
    public ZhiHuDescriblePresent(ZhiHuService zhiHuService) {
        this.mZhiHuService = zhiHuService;
    }

    public void getZhiHuStory(String id) {
        mCompositeSubscription.add(mZhiHuService.getZhihuStory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.onLoading();
                    }
                })
                .subscribe(new ResponseObserver<ZhihuStory>() {


                    @Override
                    public void onSuccess(ZhihuStory zhihuStory) {
                        mView.onLoadSuccess(zhihuStory);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onLoadError();
                    }
                }));
    }

}
