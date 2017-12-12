package com.wenping.yizhi.yizhiapp.ui.fragment.home.contract;

import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.IBaseFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.IBaseModel;

/**
 * Created by YinZeTong on 2017/12/12.
 */

public interface HomeMainContract {
    //主页接口
    abstract class HomeMainPresenter extends BasePresenter<IHomeMainModel, IHomeMainView> {
        public abstract void getTabList();
    }

    interface IHomeMainModel extends IBaseModel {
        String[] getTabs();
    }

    interface IHomeMainView extends IBaseFragment {
        void showTabList(String[] tabs);
    }
}
