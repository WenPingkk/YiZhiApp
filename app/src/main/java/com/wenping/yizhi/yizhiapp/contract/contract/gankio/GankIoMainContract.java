package com.wenping.yizhi.yizhiapp.contract.contract.gankio;


import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.IBaseFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.IBaseModel;

/**
 * Created by Horrarndoo on 2017/10/7.
 * <p>
 */

public interface GankIoMainContract {
    //主页接口
    abstract class GankIoMainPresenter extends BasePresenter<IGankIoMainModel, IGankIoMainView> {
        public abstract void getTabList();
    }

    interface IGankIoMainModel extends IBaseModel {
        String[] getTabs();
    }

    interface IGankIoMainView extends IBaseFragment {
        void showTabList(String[] tabs);
    }
}
