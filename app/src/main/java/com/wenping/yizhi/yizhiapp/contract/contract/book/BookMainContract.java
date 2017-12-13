package com.wenping.yizhi.yizhiapp.contract.contract.book;

import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.IBaseFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.IBaseModel;

/**
 * Created by Horrarndoo on 2017/10/21.
 * <p>
 */

public interface BookMainContract {
    //book主页接口
    abstract class BookMainPresenter extends BasePresenter<IBookMainModel, IBookMainView> {
        public abstract void getTabList();
    }

    interface IBookMainModel extends IBaseModel {
        String[] getTabs();
    }

    interface IBookMainView extends IBaseFragment {
        void showTabList(String[] tabs);
    }
}
