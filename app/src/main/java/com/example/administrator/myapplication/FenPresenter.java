package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2018/1/19/019.
 */

public class FenPresenter implements FenModel.ScuMod {

    IFenView view;
    FenModel model;

    public FenPresenter(IFenView view) {
        this.view = view;
        model = new FenModel();
        model.setScuMod(this);
    }

    @Override
    public void Zc(Zean bean) {
        view.onZuo(bean);
    }

    @Override
    public void Yc(Yean yean) {
        view.onYou(yean);
    }


    //左侧调用
    public void  ShowPer(){
        model.ZuoChen();
    }
    // 右侧调用
    public void FlShowYou(int cont){
        model.YuoChen(cont);
    }

}