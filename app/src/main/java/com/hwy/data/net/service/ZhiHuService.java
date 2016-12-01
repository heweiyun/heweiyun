package com.hwy.data.net.service;

import com.hwy.data.model.ZhihuStory;
import com.hwy.data.net.response.ZhihuDailyResp;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/28 17:59
 * 修改人：heweiyun
 * 修改时间：2016/11/28 17:59
 * 修改备注：
 */

public interface ZhiHuService {

    @GET("/api/4/news/latest")
    Observable<ZhihuDailyResp> getLastDaily();

    @GET("/api/4/news/before/{date}")
    Observable<ZhihuDailyResp> getTheDaily(@Path("date") String date);

    @GET("/api/4/news/{id}")
    Observable<ZhihuStory> getZhihuStory(@Path("id") String id);
}
