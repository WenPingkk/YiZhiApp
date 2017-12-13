package com.wenping.yizhi.yizhiapp.contract.contract.movie;

import android.widget.ImageView;

import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.HotMovieBean;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.child.SubjectsBean;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.IBaseFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.IBaseModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/10/16.
 * <p>
 */

public interface MovieMainContract {
    abstract class MovieMainPresenter extends BasePresenter<IMovieMainModel, IMovieMainView> {
        /**
         * 加载最新的最热电影
         */
        public abstract void loadHotMovieList();

        /**
         * item点击事件
         *
         * @param position  position
         * @param item      item
         * @param imageView imageView
         */
        public abstract void onItemClick(int position, SubjectsBean item, ImageView imageView);

        /**
         * Header被点击
         */
        public abstract void onHeaderClick();
    }

    interface IMovieMainModel extends IBaseModel {
        /**
         * 获取最热电影
         *
         * @return 最热电影
         */
        Observable<HotMovieBean> getHotMovieList();
    }

    interface IMovieMainView extends IBaseFragment {
        /**
         * 更新界面list
         *
         * @param list list
         */
        void updateContentList(List<SubjectsBean> list);

        /**
         * 显示网络错误
         */
        void showNetworkError();
    }
}
