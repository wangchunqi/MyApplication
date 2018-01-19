package com.example.administrator.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

/**
 * Created by Administrator on 2018/1/19/019.
 */

public class TwoFragment extends Fragment implements IFenView {

    private View view;
    private RecyclerView rec;
    public FenPresenter presenter;
    public RecyclerView zrv;
    public ExpandableListView yev;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fenlei, null);
        presenter = new FenPresenter(this);
        presenter.ShowPer();
        //默认展示第一条
        presenter.FlShowYou(1);
        //找控件
        zrv = view.findViewById(R.id.zrv);
        yev = view.findViewById(R.id.yev);


        return view;
    }


    @Override
    public void onZuo(final Zean zean) {
        zrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //拿到适配器
        ZuoAdapter adapter = new ZuoAdapter(getActivity(), zean);
        zrv.setAdapter(adapter);
        //调用点击事件
        adapter.setOnItemClickListener(new ZuoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int id = zean.getData().get(position).getCid();
                presenter.FlShowYou(id);

            }
        });
    }

    @Override
    public void onYou(Yean yean) {
        ErAdapter adapter = new ErAdapter(getActivity(), yean);
        yev.setAdapter(adapter);
        //父级列表默认全部展开
        int groupCount = yev.getCount();
        for (int i = 0; i < groupCount; i++) {
            yev.expandGroup(i);


        }


    }
}