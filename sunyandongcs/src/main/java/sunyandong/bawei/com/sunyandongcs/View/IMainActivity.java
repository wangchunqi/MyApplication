package sunyandong.bawei.com.sunyandongcs.View;

import java.util.List;

import sunyandong.bawei.com.sunyandongcs.Bean.BaseBean;
import sunyandong.bawei.com.sunyandongcs.Bean.GetCarsBean;

/**
 * Created by 悻 on 2018/1/19.
 */

public interface IMainActivity {
    //显示购物车的信息
    public void showList(List<GetCarsBean.DataBean> groupList, List<List<GetCarsBean.DataBean.ListBean>> childList);
    //显示创建订单
    public void showCreateOrder(BaseBean createOrderBean);
}
