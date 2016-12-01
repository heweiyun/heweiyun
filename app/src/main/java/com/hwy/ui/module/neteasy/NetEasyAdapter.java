package com.hwy.ui.module.neteasy;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwy.R;
import com.hwy.common.wrapper.ImageLoader;
import com.hwy.data.model.NetEasyNewsItem;

import java.util.List;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/30 14:31
 * 修改人：heweiyun
 * 修改时间：2016/11/30 14:31
 * 修改备注：
 */

public class NetEasyAdapter extends BaseQuickAdapter<NetEasyNewsItem,BaseViewHolder> {

    public NetEasyAdapter(List<NetEasyNewsItem> data) {
        super(R.layout.item_neteasy, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, NetEasyNewsItem netEasyNewsItem) {
        ImageLoader.load(netEasyNewsItem.imgsrc,(ImageView) baseViewHolder.getView(R.id.item_neteasy_imv_icon));
        baseViewHolder.setText(R.id.item_neteasy_tv_source,netEasyNewsItem.source);
        baseViewHolder.setText(R.id.item_neteasy_tv_title,netEasyNewsItem.title);
    }
}
