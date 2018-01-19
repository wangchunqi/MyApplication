package sunyandong.bawei.com.sunyandongcs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sunyandong.bawei.com.sunyandongcs.Adapter.OrderAdapter;
import sunyandong.bawei.com.sunyandongcs.Bean.GetOrdersBean;
import sunyandong.bawei.com.sunyandongcs.Persenter.MainPresenter;
import sunyandong.bawei.com.sunyandongcs.View.IOrderActivity;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener ,IOrderActivity {

    private ImageView mIvType;
    /**
     * 待支付
     */
    private TextView mDaizhifu;
    /**
     * 已支付
     */
    private TextView mYizhifu;
    /**
     * 已取消
     */
    private TextView mYiquxiao;
    private RecyclerView mRv;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        mainPresenter = new MainPresenter(this);
        mainPresenter.getOrders("0");
    }

    private void initView() {
        mIvType = (ImageView) findViewById(R.id.ivType);
        mIvType.setOnClickListener(this);
        mDaizhifu = (TextView) findViewById(R.id.daizhifu);
        mDaizhifu.setOnClickListener(this);
        mYizhifu = (TextView) findViewById(R.id.yizhifu);
        mYizhifu.setOnClickListener(this);
        mYiquxiao = (TextView) findViewById(R.id.yiquxiao);
        mYiquxiao.setOnClickListener(this);
        mRv = (RecyclerView) findViewById(R.id.rv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivType:
                //弹出popupwindow
                View view = View.inflate(OrderActivity.this, R.layout.pop_item, null);
                TextView tv1 = view.findViewById(R.id.tv1);
                TextView tv2 = view.findViewById(R.id.tv2);
                TextView tv3 = view.findViewById(R.id.tv3);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                PopupWindow popupWindow = new PopupWindow(view, layoutParams.width, layoutParams.height);
                popupWindow.showAsDropDown(mIvType, 0, 30);
                tv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(OrderActivity.this, "tv1", Toast.LENGTH_SHORT).show();
                        mainPresenter.getOrders("0");
                    }
                });
                tv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(OrderActivity.this, "tv2", Toast.LENGTH_SHORT).show();
                        mainPresenter.getOrders("1");
                    }
                });
                tv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(OrderActivity.this, "tv3", Toast.LENGTH_SHORT).show();
                        mainPresenter.getOrders("2");
                    }
                });
                break;
            case R.id.daizhifu:
                mainPresenter.getOrders("0");
                break;
            case R.id.yizhifu:
                mainPresenter.getOrders("1");
                break;
            case R.id.yiquxiao:
                mainPresenter.getOrders("2");
                break;
        }
    }

    @Override
    public void showOrder(List<GetOrdersBean.DataBean> beanList) {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        OrderAdapter orderAdapter = new OrderAdapter(OrderActivity.this, beanList);
        mRv.setAdapter(orderAdapter);
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.onDeath();
    }
}
