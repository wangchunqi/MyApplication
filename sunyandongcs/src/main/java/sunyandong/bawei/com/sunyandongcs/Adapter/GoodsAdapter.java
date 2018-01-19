package sunyandong.bawei.com.sunyandongcs.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import sunyandong.bawei.com.sunyandongcs.Bean.GetCarsBean;
import sunyandong.bawei.com.sunyandongcs.Bean.MessageEvent;
import sunyandong.bawei.com.sunyandongcs.Bean.PriceAndCountEvent;
import sunyandong.bawei.com.sunyandongcs.R;

/**
 * Created by 悻 on 2018/1/19.
 */

public class GoodsAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<GetCarsBean.DataBean> groupList;
    private List<List<GetCarsBean.DataBean.ListBean>> childList;
    private LayoutInflater inflater;

    public GoodsAdapter(Context context, List<GetCarsBean.DataBean> groupList, List<List<GetCarsBean.DataBean.ListBean>> childList) {
        this.context = context;
        this.groupList = groupList;
        this.childList = childList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, final ViewGroup parent) {
        View view;
        final GroupViewHolder holder;
        if (convertView == null){
            holder = new GroupViewHolder();
            view = inflater.inflate(R.layout.item_parent,null);
            holder.cbGroup = view.findViewById(R.id.check_parent);
            holder.tv_number = view.findViewById(R.id.tv_num);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (GroupViewHolder) view.getTag();
        }
        final GetCarsBean.DataBean dataBean = groupList.get(groupPosition);
        holder.cbGroup.setChecked(dataBean.isGroupCheck());
        holder.tv_number.setText(dataBean.getSellerName());
        //一级列表点击事件
        holder.cbGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //一级的选中状态
                boolean flag = holder.cbGroup.isChecked();
                //于bean类关联
                dataBean.setGroupCheck(flag);
                //调用点击一级的选中改变二级的状态的方法
                setChangChildCheckBoxState(groupPosition,flag);
                //判断是否需要勾选全选按钮(一级的是否全部勾选)
                changAllState(isAllGroupCheckBoxSelected());
                //计算总价
                EventBus.getDefault().post(computerTotal());
                //刷新适配器
                notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        final ChildViewHolder holder;
        if (convertView == null) {
            holder = new ChildViewHolder();
            view = inflater.inflate(R.layout.item_child, null);
            holder.tupian = view.findViewById(R.id.tupian);
            holder.cbChild = view.findViewById(R.id.cb_child);
            holder.tv_tel = view.findViewById(R.id.tv_tel);
            holder.tv_content = view.findViewById(R.id.tv_content);
            holder.tv_time = view.findViewById(R.id.tv_time);
            holder.tv_price = view.findViewById(R.id.tv_pri);
            holder.iv_add = view.findViewById(R.id.iv_add);
            holder.iv_del = view.findViewById(R.id.iv_del);
            holder.tv_num = view.findViewById(R.id.tv_num);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ChildViewHolder) view.getTag();
        }
        final GetCarsBean.DataBean.ListBean bean = childList.get(groupPosition).get(childPosition);
        String[] split = bean.getImages().split("\\|");
        Glide.with(context).load(split[0]).into(holder.tupian);
        holder.cbChild.setChecked(bean.isChildCheck());
        holder.tv_tel.setText(bean.getTitle().substring(0,15));
        holder.tv_content.setText(bean.getPid()+"");
        holder.tv_time.setText(bean.getCreatetime());
        holder.tv_price.setText(bean.getPrice() + "");
        holder.tv_num.setText(bean.getNum() + "");
        //二级的选中状态
        holder.cbChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取选是否选中
                boolean flag = holder.cbChild.isChecked();
                //与Bean关联
                bean.setChildCheck(flag);
                //判断是否选中和是否全部选中
                if (holder.cbChild.isChecked()) {
                    //当前checkbox是选中状态
                    if (isAllChildCheckBoxSelected(groupPosition)) {
                        setChangGroupCheckBoxState(groupPosition, true);
                        changAllState(isAllGroupCheckBoxSelected());
                    }
                } else {
                    setChangGroupCheckBoxState(groupPosition, false);
                    changAllState(isAllGroupCheckBoxSelected());
                }
                //计算总价并发送
                EventBus.getDefault().post(computerTotal());
                //刷新适配器
                notifyDataSetChanged();
            }
        });
        //加号的点击事件
        holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = bean.getNum();
                holder.tv_num.setText(++num+"");
                bean.setNum(num);
                //计算总价并发送
                EventBus.getDefault().post(computerTotal());
                //刷新适配器
                notifyDataSetChanged();
            }
        });
        //减号的点击事件
        holder.iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = bean.getNum();
                if (num==1){
                    Toast.makeText(context,"宝贝不能被在减少了!!!",Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.tv_num.setText(--num+"");
                bean.setNum(num);
                //计算总价并发送
                EventBus.getDefault().post(computerTotal());
                //刷新适配器
                notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    class GroupViewHolder {
        CheckBox cbGroup;
        TextView tv_number;
    }

    class ChildViewHolder {
        ImageView tupian;
        CheckBox cbChild;
        TextView tv_tel;
        TextView tv_content;
        TextView tv_time;
        TextView tv_price;
        ImageView iv_del;
        ImageView iv_add;
        TextView tv_num;
    }
    /**
     * 一级点击改变二级的状态
     * @param groupPosition
     * @param flag
     */
    private void setChangChildCheckBoxState(int groupPosition,boolean flag){
        List<GetCarsBean.DataBean.ListBean> listBeans = childList.get(groupPosition);
        for (int i = 0; i < listBeans.size(); i++) {
            GetCarsBean.DataBean.ListBean listBean = listBeans.get(i);
            listBean.setChildCheck(flag);
        }
        notifyDataSetChanged();
    }

    /**
     * 二级改变一级的状态
     * @param groupPosition
     * @param flag
     */
    private void setChangGroupCheckBoxState(int groupPosition,boolean flag){
        GetCarsBean.DataBean dataBean = groupList.get(groupPosition);
        dataBean.setGroupCheck(flag);
        notifyDataSetChanged();
    }
    /**
     * 计算总价
     * @return
     */
    private PriceAndCountEvent computerTotal(){
        int count = 0;
        int price = 0;
        for (int i = 0; i < childList.size(); i++) {
            List<GetCarsBean.DataBean.ListBean> listBeans = childList.get(i);
            for (int j = 0; j < listBeans.size(); j++) {
                GetCarsBean.DataBean.ListBean listBean = listBeans.get(j);
                if (listBean.isChildCheck()){
                    count += listBean.getNum();
                    price += listBean.getNum()*listBean.getPrice();
                }
            }
        }
        PriceAndCountEvent event = new PriceAndCountEvent();
        event.setCount(count);
        event.setPrice(price);
        return event;
    }

    /**
     * 判断一级是否全选
     * @return
     */
    private boolean isAllGroupCheckBoxSelected(){
        for (int i = 0; i < groupList.size(); i++) {
            GetCarsBean.DataBean dataBean = groupList.get(i);
            if (!dataBean.isGroupCheck()){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断二级是否全选
     * @param groupPosition
     * @return
     */
    private boolean isAllChildCheckBoxSelected(int groupPosition){
        List<GetCarsBean.DataBean.ListBean> listBeans = childList.get(groupPosition);
        for (int i = 0; i < listBeans.size(); i++) {
            GetCarsBean.DataBean.ListBean listBean = listBeans.get(i);
            if (!listBean.isChildCheck()){
                return false;
            }
        }
        return true;
    }

    /**
     * 改变全选状态
     * @param flag
     */
    private void changAllState(boolean flag){
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setChecked(flag);
        EventBus.getDefault().post(messageEvent);
    }

    /**
     * 全选反选
     * @param flag
     */
    public void setAll(boolean flag){
        for (int i = 0; i < groupList.size(); i++) {
            setChangGroupCheckBoxState(i,flag);
            setChangChildCheckBoxState(i,flag);
        }
        //计算总价并发送
        EventBus.getDefault().post(computerTotal());
        //刷新适配器
        notifyDataSetChanged();
    }
}
