package com.wenping.yizhi.yizhiapp.contract.contract.home;

import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.IBaseFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.IBaseModel;

/**
 * Created by Horrarndoo on 2017/9/11.
 * <p>
 * 主页Contract
 */

public interface HomeMainContract {
    //在接口里创建了抽象类...,并且创建了一个抽象方法
    //主页接口
    abstract class HomeMainPresenter extends BasePresenter<IHomeMainModel, IHomeMainView> {
        public abstract void getTabList();
    }
    //创建子接口,继承自IBaseModel接口
    interface IHomeMainModel extends IBaseModel {
        String[] getTabs();
    }
    //创建子接口,继承IBaseFragment接口
    interface IHomeMainView extends IBaseFragment {
        void showTabList(String[] tabs);
    }
}
