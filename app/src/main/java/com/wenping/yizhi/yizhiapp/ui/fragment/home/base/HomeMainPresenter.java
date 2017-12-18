package com.wenping.yizhi.yizhiapp.ui.fragment.home.base;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.contract.contract.home.HomeMainContract;
import com.wenping.yizhi.yizhiapp.model.home.HomeMainModel;


/**
 * Created by Horrarndoo on 2017/9/11.
 * <p>
 */

public class HomeMainPresenter extends HomeMainContract.HomeMainPresenter {

    //返回HomeMainPresenter实例
    @NonNull
    public static HomeMainPresenter newInstance() {
        return new HomeMainPresenter();
    }

    //重写了HomeMainContract.HomeMainPresenter的方法
    @Override
    public void getTabList() {
        if (mIView == null || mIModel == null) {
            return;
        }else {
            //HomeMainContract.HomeMainPresenter集成自BasePresenter;.当前泛型mIView对应的是IHomeMainView对象
            //mIModel 对应的是IHomeMainModel
            mIView.showTabList(mIModel.getTabs())

            ;
        }
    }

    //返回 HomeMainContract.IHomeMainModel的实例
    // HomeMainModel extends BaseModel implements HomeMainContract.IHomeMainModel
    @Override
    public HomeMainContract.IHomeMainModel getModel() {
        return HomeMainModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}
