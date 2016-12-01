package com.hwy.ui.module.zhihu;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwy.R;
import com.hwy.common.wrapper.ImageLoader;
import com.hwy.data.model.ZhihuDailyItem;

import java.util.List;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/29 15:05
 * 修改人：heweiyun
 * 修改时间：2016/11/29 15:05
 * 修改备注：
 */

public class ZhiHuAdapter extends BaseQuickAdapter<ZhihuDailyItem,BaseViewHolder> {

    public ZhiHuAdapter(List<ZhihuDailyItem> data) {
        super(R.layout.item_zhihu, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ZhihuDailyItem zhihuDailyItem) {
        baseViewHolder.setText(R.id.item_zhihu_tv_title,zhihuDailyItem.title);
        ImageLoader.load(zhihuDailyItem.images[0],(ImageView)baseViewHolder.getView(R.id.item_zhihu_imv_icon));
    }
}
