package sunyandong.bawei.com.sunyandongcs.View;

import java.util.List;

import sunyandong.bawei.com.sunyandongcs.Bean.GetOrdersBean;

/**
 * Created by 悻 on 2018/1/19.
 */

public interface IOrderActivity {
    //显示订单数据
    public void showOrder(List<GetOrdersBean.DataBean> beanList);
}
