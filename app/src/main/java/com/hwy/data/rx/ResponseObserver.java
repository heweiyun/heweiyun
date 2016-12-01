package com.hwy.data.rx;

import com.hwy.common.util.LogUtil;

import rx.Subscriber;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/29 11:09
 * 修改人：heweiyun
 * 修改时间：2016/11/29 11:09
 * 修改备注：
 */

public abstract class ResponseObserver<T> extends Subscriber<T> {
    private static final String TAG = ResponseObserver.class.getSimpleName();

    @Override
    public void onCompleted() {
        LogUtil.d(TAG,"onCompleted");
    }


    @Override
    public void onNext(T t) {
        LogUtil.d(TAG,"onNext");
        onSuccess(t);
    }

    public abstract void onSuccess(T t);
}
