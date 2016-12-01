package com.hwy.present.base;

import com.hwy.ui.base.IBaseView;

import rx.subscriptions.CompositeSubscription;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/15 16:43
 * 修改人：heweiyun
 * 修改时间：2016/11/15 16:43
 * 修改备注：
 */

public class BasePresent<V extends IBaseView> implements IBasePresent<V> {

    protected V mView;

    protected CompositeSubscription mCompositeSubscription;

    @Override
    public void attachView(V v) {
        this.mView = v;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        this.mView = null;
        mCompositeSubscription.clear();
        mCompositeSubscription = null;
    }

    public boolean isAttached(){
        return mView != null;
    }

    public V getView(){
        return mView;
    }

}
