package sunyandong.bawei.com.sunyandongcs.Utils;

/**
 * Created by 悻 on 2018/1/19.
 */

public interface Api {
    //获得购物车的接口
    public static final String GETCARS = "https://www.zhaoapi.cn/product/getCarts";
    //创建订单接口
    public static final String CREATEORDER = "https://www.zhaoapi.cn/product/createOrder";
    //获取订单列表接口
    public static final String GETORDERS = "https://www.zhaoapi.cn/product/getOrders";
    //.修改订单状态
    public static final String UPDATEORDER = "https://www.zhaoapi.cn/product/updateOrder";
}
