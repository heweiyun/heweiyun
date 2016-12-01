package com.hwy.present.neteasy;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hwy.common.util.LogUtil;
import com.hwy.data.net.response.NetEasyNewsDetailResp;
import com.hwy.data.net.service.NetEasyDetailService;
import com.hwy.data.rx.ResponseObserver;
import com.hwy.present.base.BasePresent;
import com.hwy.ui.module.neteasy.INetEasyDetailView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/30 17:45
 * 修改人：heweiyun
 * 修改时间：2016/11/30 17:45
 * 修改备注：
 */

public class NetEasyDetailPresent extends BasePresent<INetEasyDetailView> {
    private static final String TAG  = NetEasyDetailPresent.class.getSimpleName();

    private NetEasyDetailService mNetEasyService;

    @Inject
    public NetEasyDetailPresent(NetEasyDetailService netEasyService) {
        mNetEasyService = netEasyService;
    }

    public void getNetEasyDetail(final String docId) {
        mCompositeSubscription.add(mNetEasyService.getNewsDetail(docId)
                .map(new Func1<String, NetEasyNewsDetailResp>() {
                    @Override
                    public NetEasyNewsDetailResp call(String s) {
                        return parseNetEastDetailJson(s, docId);
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
                .subscribe(new ResponseObserver<NetEasyNewsDetailResp>() {
                    @Override
                    public void onSuccess(NetEasyNewsDetailResp netEasyNewsDetailResp) {
                        mView.showContent(netEasyNewsDetailResp);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG,e);
                        mView.showLoadContentError();
                    }
                }));
    }

    private NetEasyNewsDetailResp parseNetEastDetailJson(String res, String docId) {
        NetEasyNewsDetailResp newsDetailBean = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get(docId);
            if (jsonElement == null) {
                return null;
            }
            Gson gson = new Gson();
            newsDetailBean = gson.fromJson(jsonElement.getAsJsonObject(), NetEasyNewsDetailResp.class);
        } catch (Exception e) {
        }
        return newsDetailBean;
    }

}
