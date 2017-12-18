##ZhiHuFragment分析

- 1.newInstance方法，返回当期那的ZhuHuFragment实例

、、、

    public static ZhihuFragment newInstance() {
        Bundle bundle = new Bundle();
        ZhihuFragment fragment = new ZhihuFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

-2.getLayoutId方法

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_zhihu;
    }

-3.initUI,实现布局的显示，当前setAdapter没有传值！

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mZhihuAdapter = new ZhihuAdapter(R.layout.item_recycle_home);
        mRvZhihu.setAdapter(mZhihuAdapter);
        mRvZhihu.setLayoutManager(new LinearLayoutManager(mActivity));
    }
-4.initPresenter方法，返回ZhiHuPresenter实例

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return ZhihuPresenter.newInstance();
    }

- 5.onLazyInitView方法，通过presenter的loadLatesList方法来获取页面view和数据吧？

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.loadLatestList();
    }
- 6.updateContentList方法，当前方法的逻辑很关键.这是BaseTabsContract接口的子接口IBaseTabsView中未实现的方法，在ZhuHuFragment中出现的另外几个方法也是重写了ta的。

、、、

    @Override
    public void updateContentList(List<ZhihuDailyItemBean> list) {
        if (mZhihuAdapter.getData().size() == 0) {
            initRecycleView(list);
        } else {
            mZhihuAdapter.addData(list);
        }
    }

    private void initRecycleView(List<ZhihuDailyItemBean> list) {
        mZhihuAdapter = new ZhihuAdapter(R.layout.item_recycle_home, list);
        mZhihuAdapter.setOnLoadMoreListener(this,mRvZhihu);
        mZhihuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position, ((ZhihuDailyItemBean) adapter.getItem(position)));
            }
        });
        mRvZhihu.setAdapter(mZhihuAdapter);
    }

- 分析：在该方法中判断 ZhiHuAdapter的数据是否为0，为0则今次那个初始化，否则就进行addData；
- 这个架构真的牛逼啊；我现在懂了！
- 在ZhihuPresenter继承了ZhihuContract.ZhihuPresenter；这个类如：ZhihuPresenter extends BaseTabsContract.BaseTabsPresenter<IZhihuModel,IZhihuView, ZhihuDailyItemBean>；
- 其中的ZhiHuPresenter类继承了父类的基类是BasePresenter有这个抽象方法，所以重写了ta的这个方法：
- 
、、、

	  /**
	     * 返回presenter想持有的Model引用
	     *
	     * @return presenter持有的Model引用
	     */
	    public abstract M getModel();

- **public void updateContentList(List<ZhihuDailyItemBean> list)方法中的数据是哪来的呢？**
- 如下：
- 1.在ZhihuPresenter中继承的基类是BaseTabContract中的抽象方法有个叫loadLatestList，重写了这个方法。在这个方法中做的操作是：
- 这里的mIModel就是ZhiHuModel；ZhihuModel实现了IZhIHuModel接口，并实现了ta未实现的方法，在各个方法中进行了网络请求获取对应的数据！！！
- 获取的数据就作为了mIView。updateContentList（xx）方法中的参数！！！
、、、

    @Override
    public void loadLatestList() {
        if (mIModel == null)
            return;
		
        mRxManager.register(mIModel.getDailyList().subscribe(new Consumer<ZhihuDailyListBean>() {
            @Override
            public void accept(ZhihuDailyListBean zhihuDailyListBean) throws Exception {
                mDate = zhihuDailyListBean.getDate();
                //Logger.e("mDate = " + mDate);

                if (mIView != null)
                    mIView.updateContentList(zhihuDailyListBean.getStories());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mIView != null) {
                    if (mIView.isVisiable())
                        mIView.showToast("Network error.");
                    mIView.showNetworkError();
                }
            }
        }));
    }

- 峰回路转，回到Zhihufragment中，重写发updateContentList方法中获取了来自zhihumodel请求而来的的数据，然后在这个方法中进行了recyclerview和adapter以及数据的整合，这就有了数据显示。如果此刻adapter》getData().size()大小为0，则把当前获取的所有数据进行添加到数据中，并notify【这是在BaseQuickAdapter中实现的】。
- ZhihuAdapter还进行了加载更多以及item的点击效果的监听：mPresenter对象响应了该点击效果实现定向的跳转。
- 这就为重写的onLazyInitView方法中调用的mPresenter调用loadLatestList方法提供了数据。

- 这就是主要部分的解析。写的有点乱。