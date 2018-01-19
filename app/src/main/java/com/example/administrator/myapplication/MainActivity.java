package com.example.administrator.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTv;
    private FrameLayout mFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                // 开启Fragment事务
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.frag,new TwoFragment()).commit();
            }
        });

    }

    private void initView() {
        mTv = (TextView) findViewById(R.id.tv);
        mFrag = (FrameLayout) findViewById(R.id.frag);
    }
}
