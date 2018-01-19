package sunyandong.bawei.com.sunyandongcs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import sunyandong.bawei.com.sunyandongcs.Adapter.GoodsAdapter;
import sunyandong.bawei.com.sunyandongcs.Bean.BaseBean;
import sunyandong.bawei.com.sunyandongcs.Bean.GetCarsBean;
import sunyandong.bawei.com.sunyandongcs.Bean.MessageEvent;
import sunyandong.bawei.com.sunyandongcs.Bean.PriceAndCountEvent;
import sunyandong.bawei.com.sunyandongcs.Persenter.MainPresenter;
import sunyandong.bawei.com.sunyandongcs.View.IMainActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMainActivity {

    private ExpandableListView mElv;
    private CheckBox mCheckAll;
    /**
     * 合计:0
     */
    private TextView mPriceAll;
    /**
     * 结算:0
     */
    private TextView mCountAll;
    private MainPresenter mainPresenter;
    private GoodsAdapter goodsAdapter;
    private ImageView mIvBack;
    private int price;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mainPresenter = new MainPresenter(this);
        mainPresenter.getCarts();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        mElv = (ExpandableListView) findViewById(R.id.elv);
        mCheckAll = (CheckBox) findViewById(R.id.checkAll);
        mPriceAll = (TextView) findViewById(R.id.priceAll);
        mCountAll = (TextView) findViewById(R.id.countAll);
        mCheckAll.setOnClickListener(this);
        mPriceAll.setOnClickListener(this);
        mIvBack = (ImageView) findViewById(R.id.ivBack);
        mIvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkAll:
                goodsAdapter.setAll(mCheckAll.isChecked());
                break;
            case R.id.priceAll:
                mainPresenter.createOrder(price+"");
                break;
            case R.id.ivBack:
                this.finish();
                break;
        }
    }

    @Override
    public void showList(List<GetCarsBean.DataBean> groupList, List<List<GetCarsBean.DataBean.ListBean>> childList) {
        goodsAdapter = new GoodsAdapter(this, groupList, childList);
        mElv.setAdapter(goodsAdapter);
        mElv.setGroupIndicator(null);
        //默认让其全部展开
        for (int i = 0; i < groupList.size(); i++) {
            mElv.expandGroup(i);
        }
    }

    @Override
    public void showCreateOrder(BaseBean createOrderBean) {
        Toast.makeText(MainActivity.this,createOrderBean.getMsg(), Toast.LENGTH_SHORT).show();
        if (createOrderBean.getCode().equals("0")) {
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.onDeath();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        mCheckAll.setChecked(event.isChecked());
    }

    @Subscribe
    public void onMessageEvent(PriceAndCountEvent event) {
        price = event.getPrice();
        count = event.getCount();
        mCountAll.setText("合计:" + count);
        mPriceAll.setText("结算:" + price);
    }
}
