package com.hwy.ui.module.zhihu;

import com.hwy.data.net.response.ZhihuDailyResp;
import com.hwy.ui.base.IBaseView;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/29 10:27
 * 修改人：heweiyun
 * 修改时间：2016/11/29 10:27
 * 修改备注：
 */

public interface IZhiHuView extends IBaseView {

    /**
     *显示加载第一页中
     */
    void showLoadingContent();


    /**
     *显示加载第一页的错误信息
     */
    void showLoadContentError();

    /**
     *显示加载更多是错误信息
     */
    void showLoadingMoreError();

    /**
     * 显示第一页加载的内容
     * @param resp
     */
    void showContent(ZhihuDailyResp resp);

    /**
     * 显示加载更多的内容
     * @param resp
     */
    void showMoreContent(ZhihuDailyResp resp);

    /**
     * 显示没有更多
     */
    void showLoadNomore();

    /**
     *显示第一页为空
     */
    void showContentEmpty();
}
