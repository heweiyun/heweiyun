package com.hwy.present.base;

import android.support.annotation.UiThread;

import com.hwy.ui.base.IBaseView;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/15 16:39
 * 修改人：heweiyun
 * 修改时间：2016/11/15 16:39
 * 修改备注：
 */

public interface IBasePresent<V extends IBaseView> {

    /**
     * 绑定视图到Present
     * @param v
     */
    @UiThread
    void attachView(V v);

    /**
     * 将视图从Present解绑
     */
    @UiThread
    void detachView();

}
