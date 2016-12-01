package com.hwy.data.net.service;

import com.hwy.data.net.response.NetEasyNewsListResp;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/30 11:26
 * 修改人：heweiyun
 * 修改时间：2016/11/30 11:26
 * 修改备注：
 */

public interface NetEasyService {

    @GET("/nc/article/headline/T1348647909107/{id}-20.html")
    Observable<NetEasyNewsListResp> getNews(@Path("id") int id );

}
