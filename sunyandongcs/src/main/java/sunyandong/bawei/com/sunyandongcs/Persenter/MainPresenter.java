package sunyandong.bawei.com.sunyandongcs.Persenter;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sunyandong.bawei.com.sunyandongcs.Bean.BaseBean;
import sunyandong.bawei.com.sunyandongcs.Bean.GetCarsBean;
import sunyandong.bawei.com.sunyandongcs.Bean.GetOrdersBean;
import sunyandong.bawei.com.sunyandongcs.Modle.IMainModel;
import sunyandong.bawei.com.sunyandongcs.Modle.MainModel;
import sunyandong.bawei.com.sunyandongcs.Utils.OnNetWorkListener;
import sunyandong.bawei.com.sunyandongcs.View.IMainActivity;
import sunyandong.bawei.com.sunyandongcs.View.IOrderActivity;

/**
 * Created by 悻 on 2018/1/19.
 */
public class MainPresenter {
    private IMainActivity iMainActivity;
    private IMainModel iMainModel;
    private IOrderActivity iOrderActivity;

    /**
     * 购物车页面
     * @param iMainActivity
     */
    public MainPresenter(IMainActivity iMainActivity) {
        this.iMainActivity = iMainActivity;
        iMainModel = new MainModel();
    }

    /**
     * 订单页面
     * @param iOrderActivity
     */
    public MainPresenter(IOrderActivity iOrderActivity) {
        this.iOrderActivity = iOrderActivity;
        iMainModel = new MainModel();
    }

    /**
     * 修改订单
     */
    public MainPresenter() {
        this.iMainModel = new MainModel();
    }
    /**
     * 加载购物车
     */
    public void getCarts(){
        HashMap<String, String> params = new HashMap<>();
        params.put("uid","1775");
        iMainModel.getGoodsCar(params, new OnNetWorkListener<GetCarsBean>() {
            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onSucceed(GetCarsBean getCarsBean) {
                if (iMainActivity != null) {
                    List<GetCarsBean.DataBean> data = getCarsBean.getData();
                    ArrayList<List<GetCarsBean.DataBean.ListBean>> childList = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        List<GetCarsBean.DataBean.ListBean> datas = data.get(i).getList();
                        childList.add(datas);
                    }
                    iMainActivity.showList(data, childList);
                }
            }
        });
    }

    /**
     * 创建订单
     * @param price
     */
    public void createOrder(String price){
        HashMap<String, String> params = new HashMap<>();
        params.put("uid","71");
        params.put("price",price);
        iMainModel.createOrder(params, new OnNetWorkListener<BaseBean>() {
            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onSucceed(BaseBean createOrderBean) {
                if (iMainActivity != null) {
                    iMainActivity.showCreateOrder(createOrderBean);
                }
            }
        });
    }

    /**
     * 订单数据展示
     */
    public void getOrders(String status){
        HashMap<String, String> params = new HashMap<>();
        params.put("uid","71");
        if (status != null){
            params.put("status",status);
        }
        iMainModel.getOrders(params, new OnNetWorkListener<GetOrdersBean>() {
            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onSucceed(GetOrdersBean getOrdersBean) {
                if (iOrderActivity != null) {
                    iOrderActivity.showOrder(getOrdersBean.getData());
                }
            }
        });
    }

    /***
     * 修改订单
     * @param status
     * @param orderId
     */
    public void updateOrder(final Context context, String status, String orderId){
        HashMap<String, String> params = new HashMap<>();
        params.put("uid","71");
        params.put("status",status);
        params.put("orderId",orderId);
        iMainModel.updateOrder(params, new OnNetWorkListener<BaseBean>() {
            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onSucceed(BaseBean baseBean) {
                Toast.makeText(context,baseBean.getMsg(),Toast.LENGTH_SHORT);
            }
        });
    }
    /**
     * 销毁
     */
    public void onDeath(){
        iMainActivity = null;
        iOrderActivity = null;
    }
}
