//package com.wenping.yizhi.yizhiapp.presenter.movie;
//
//import android.support.annotation.NonNull;
//import android.widget.ImageView;
//
//import com.wenping.yizhi.yizhiapp.contract.contract.movie.MovieMainContract;
//import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.HotMovieBean;
//import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.child.SubjectsBean;
//import com.wenping.yizhi.yizhiapp.model.movie.MovieMainModel;
//
//import io.reactivex.functions.Consumer;
//
///**
// * Created by Horrarndoo on 2017/10/16.
// * <p>
// */
//
//public class MovieMainPresenter extends MovieMainContract.MovieMainPresenter {
//
//    @NonNull
//    public static MovieMainPresenter newInstance() {
//        return new MovieMainPresenter();
//    }
//
//    @Override
//    public void loadHotMovieList() {
//        if (mIModel == null || mIView == null)
//            return;
//
//        mRxManager.register(mIModel.getHotMovieList().subscribe(new Consumer<HotMovieBean>() {
//            @Override
//            public void accept(HotMovieBean hotMovieBean) throws Exception {
//                if (mIView == null)
//                    return;
//
//                mIView.updateContentList(hotMovieBean.getSubjects());
//                Cache.saveHotMovieCache(hotMovieBean.getSubjects());
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                if (mIView != null) {
//                    if (mIView.isVisiable())
//                        mIView.showToast("Network error.");
//
//                    if (Cache.getHotMovieCache().size() == 0) {//没有缓存缓存，显示网络错误界面
//                        mIView.showNetworkError();
//                    } else {
//                        mIView.updateContentList(Cache.getHotMovieCache());//加载缓存
//                    }
//                }
//            }
//        }));
//    }
//
//    @Override
//    public void onItemClick(int position, SubjectsBean item, ImageView imageView) {
////        Logger.e("position " + position + " is clicked.");
//        MovieDetailActivity.start(mIView.getBindActivity(), item, imageView);
//    }
//
//    @Override
//    public void onHeaderClick() {
//        mIView.startNewFragment(TopMoiveFragment.newInstance());
//    }
//
//    @Override
//    public MovieMainContract.IMovieMainModel getModel() {
//        return MovieMainModel.newInstance();
//    }
//
//    @Override
//    public void onStart() {
//    }
//}
