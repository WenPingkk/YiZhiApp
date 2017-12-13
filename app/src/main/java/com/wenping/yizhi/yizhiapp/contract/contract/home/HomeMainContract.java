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
