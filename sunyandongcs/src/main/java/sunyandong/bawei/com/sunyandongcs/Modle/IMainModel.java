package sunyandong.bawei.com.sunyandongcs.Modle;

import java.util.Map;

import sunyandong.bawei.com.sunyandongcs.Bean.BaseBean;
import sunyandong.bawei.com.sunyandongcs.Bean.GetCarsBean;
import sunyandong.bawei.com.sunyandongcs.Bean.GetOrdersBean;
import sunyandong.bawei.com.sunyandongcs.Utils.OnNetWorkListener;

/**
 * Created by 悻 on 2018/1/19.
 */

public interface IMainModel {
    //获取购物车信息
    void getGoodsCar(Map<String,String> params, OnNetWorkListener<GetCarsBean> onNetWorkListener);
    //创建订单
    void createOrder(Map<String,String> params, OnNetWorkListener<BaseBean> onNetWorkListener);
    //显示订单
    void getOrders(Map<String,String> params, OnNetWorkListener<GetOrdersBean> onNetWorkListener);
    //修改订单
    void updateOrder(Map<String,String> params, OnNetWorkListener<BaseBean> onNetWorkListener);
}
