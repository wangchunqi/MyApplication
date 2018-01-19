package com.example.administrator.myapplication;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/1/19/019.
 */

public interface Inters {
    @GET("product/getCatagory")
    Observable<Zean> Zuo();
    /**
     * 右面接口
     */
    @GET("product/getProductCatagory")
    Observable<Yean> You(@Query("cid") int cid);

}
