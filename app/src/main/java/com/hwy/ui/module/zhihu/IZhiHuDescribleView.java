package com.hwy.ui.module.zhihu;

import com.hwy.data.model.ZhihuStory;
import com.hwy.ui.base.IBaseView;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/29 17:31
 * 修改人：heweiyun
 * 修改时间：2016/11/29 17:31
 * 修改备注：
 */

public interface IZhiHuDescribleView extends IBaseView{

    void showLoadingContent();

    void showLoadContentError();

    void showContent(ZhihuStory story);




}
