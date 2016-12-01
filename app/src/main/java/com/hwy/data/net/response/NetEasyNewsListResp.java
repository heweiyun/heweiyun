package com.hwy.data.net.response;

import com.google.gson.annotations.SerializedName;
import com.hwy.data.model.NetEasyNewsItem;

import java.util.List;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/30 11:21
 * 修改人：heweiyun
 * 修改时间：2016/11/30 11:21
 * 修改备注：
 */

public class NetEasyNewsListResp {

    @SerializedName("T1348647909107")
    public List<NetEasyNewsItem> newsList;
}
