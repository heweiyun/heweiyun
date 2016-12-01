package com.hwy.ui.module.neteasy;

import com.hwy.data.model.NetEasyNewsItem;
import com.hwy.ui.base.IBaseView;

import java.util.List;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/30 11:36
 * 修改人：heweiyun
 * 修改时间：2016/11/30 11:36
 * 修改备注：
 */

public interface INetEasyView extends IBaseView{

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
     * @param dataList
     */
    void showContent(List<NetEasyNewsItem> dataList);

    /**
     * 显示加载更多的内容
     * @param dataList
     */
    void showMoreContent(List<NetEasyNewsItem> dataList);

    /**
     * 显示没有更多
     */
    void showLoadNomore();

    /**
     *显示第一页为空
     */
    void showContentEmpty();
}
