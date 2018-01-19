package sunyandong.bawei.com.sunyandongcs.Modle;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import sunyandong.bawei.com.sunyandongcs.Bean.BaseBean;
import sunyandong.bawei.com.sunyandongcs.Bean.GetCarsBean;
import sunyandong.bawei.com.sunyandongcs.Bean.GetOrdersBean;
import sunyandong.bawei.com.sunyandongcs.Utils.Api;
import sunyandong.bawei.com.sunyandongcs.Utils.HttpUtils;
import sunyandong.bawei.com.sunyandongcs.Utils.OnNetWorkListener;

/**
 * Created by 悻 on 2018/1/19.
 */
public class MainModel implements IMainModel{
    //创建Handler
    private Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 购物车数据展示
     * @param params
     * @param onNetWorkListener
     */
    @Override
    public void getGoodsCar(Map<String, String> params, final OnNetWorkListener<GetCarsBean> onNetWorkListener) {
        //网络请求
        HttpUtils.getHttpUtils().doPost(Api.GETCARS, params, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //失败的回调
                        onNetWorkListener.onFailure(e);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final GetCarsBean getCarsBean = new Gson().fromJson(string, GetCarsBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //成功的回调
                        onNetWorkListener.onSucceed(getCarsBean);
                    }
                });
            }
        });
    }

    /**
     * 创建订单
     * @param params
     * @param onNetWorkListener
     */
    @Override
    public void createOrder(Map<String, String> params, final OnNetWorkListener<BaseBean> onNetWorkListener) {
        HttpUtils.getHttpUtils().doPost(Api.CREATEORDER, params, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //失败的回调
                        onNetWorkListener.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final BaseBean createOrderBean = new Gson().fromJson(string, BaseBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //成功的回调
                        onNetWorkListener.onSucceed(createOrderBean);
                    }
                });
            }
        });
    }

    /**
     * 订单数据展示
     * @param params
     * @param onNetWorkListener
     */
    @Override
    public void getOrders(Map<String, String> params, final OnNetWorkListener<GetOrdersBean> onNetWorkListener) {
        HttpUtils.getHttpUtils().doPost(Api.GETORDERS, params, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //失败的回调
                        onNetWorkListener.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final GetOrdersBean getOrdersBean = new Gson().fromJson(string, GetOrdersBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //成功的回调
                        onNetWorkListener.onSucceed(getOrdersBean);
                    }
                });
            }
        });
    }

    /**
     * 修改订单
     * @param params
     * @param onNetWorkListener
     */
    @Override
    public void updateOrder(Map<String, String> params, final OnNetWorkListener<BaseBean> onNetWorkListener) {
        HttpUtils.getHttpUtils().doPost(Api.GETORDERS, params, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //失败的回调
                        onNetWorkListener.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final BaseBean baseBean = new Gson().fromJson(string, BaseBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //成功的回调
                        onNetWorkListener.onSucceed(baseBean);
                    }
                });
            }
        });
    }
}
