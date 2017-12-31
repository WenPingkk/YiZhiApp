package com.wenping.yizhi.yizhiapp.ui.fragment.book.child.fragmenttabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wansir.lib.logger.Logger;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.adapter.BookCustomAdapter;
import com.wenping.yizhi.yizhiapp.constant.BundleKeyConstant;
import com.wenping.yizhi.yizhiapp.contract.contract.book.tabs.BookCustomContract;
import com.wenping.yizhi.yizhiapp.model.bean.douban.book.BookItemBean;
import com.wenping.yizhi.yizhiapp.presenter.book.tabs.BookCustomPresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseRecycleFragment;

import java.util.List;

import butterknife.BindView;

/**
 * @author WenPing
 * @date 12/30/2017
 */

public class BookCustomFragment extends BaseRecycleFragment<BookCustomContract
        .BookCustomPresenter, BookCustomContract.IBookCustomModel> implements BookCustomContract
        .IBookCustomView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_book)
    RecyclerView mRvBook;

    private BookCustomAdapter mBookCustomAdapter;
    private String mBookTags = "文学";

    public static BookCustomFragment newInstance(String bookTags) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeyConstant.ARG_KEY_DOUBAN_BOOK_TAGS, bookTags);
        BookCustomFragment fragment = new BookCustomFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.loadLatestBookList();
    }

    @Override
    public void initData() {
        super.initData();
        Bundle args = getArguments();
        if (args != null) {
            mBookTags = args.getString(BundleKeyConstant.ARG_KEY_DOUBAN_BOOK_TAGS);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_book_custom;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mBookCustomAdapter = new BookCustomAdapter(R.layout.item_book_custom);
        mRvBook.setAdapter(mBookCustomAdapter);
        mRvBook.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return BookCustomPresenter.newInstance();
    }

    @Override
    public void updateContentList(List<BookItemBean> list) {
        Logger.e(list.toString());
        if (mBookCustomAdapter.getData().size() == 0) {
            initRecycleView(list);
        } else {
            mBookCustomAdapter.addData(list);
        }
    }

    private void initRecycleView(List<BookItemBean> list) {
        mBookCustomAdapter = new BookCustomAdapter(R.layout.item_book_custom, list);
        mBookCustomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position,(BookItemBean) adapter.getItem(position),(ImageView) view.findViewById(R.id.iv_item_image));
            }
        });
        mBookCustomAdapter.setOnLoadMoreListener(this,mRvBook);
        mRvBook.setAdapter(mBookCustomAdapter);
    }

    @Override
    public void showNetworkError() {
        mBookCustomAdapter.setEmptyView(errorView);
    }

    @Override
    public void showLoadMoreError() {
        mBookCustomAdapter.loadMoreFail();
    }

    @Override
    public void showNoMoreData() {
        mBookCustomAdapter.loadMoreEnd(true);
    }

    @Override
    public String getBookTags() {
        return mBookTags;
    }

    @Override
    protected void onErrorViewClick(View view) {
        mPresenter.loadLatestBookList();
    }

    @Override
    protected void showLoading() {
        mBookCustomAdapter.setEmptyView(loadingView);
    }


    @Override
    public void onLoadMoreRequested() {
        mBookCustomAdapter.loadMoreComplete();
        mPresenter.loadMoreBookList();
    }
}
