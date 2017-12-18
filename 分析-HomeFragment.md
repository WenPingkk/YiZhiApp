## 一之

### 架构方式 mvp.
##这个MVP架构方式，用到的接口很多很多！！！重点是presenter部分。
- 重点分析:homeframent应用到的mvp框架.

- HomeRootFragment extends BaseCompatFragment;在initUI方法中将HomeFragment实例填充到自己的Flamelayout中.+
- public class HomeFragment extends BaseMVPCompatFragment<HomeMainContract.HomeMainPresenter,
        HomeMainContract.IHomeMainModel> implements HomeMainContract.IHomeMainView 
- HomeFragment牵涉到的抽象和架构很深刻
###1.用到了泛型.泛型中用到的两个接口分别是:
- 1.HomeMainContract.HomeMainPresenter，以此实现getTabList方法

、、、

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

- 2.HomeMainContract.IHomeMainModel，以此实现getTabs方法。

、、、

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
- 以上两个接口都是HomeMainContract的子接口
- 并单独实现了一个接口HomeMainContract.IHomeMainView，实现了showTabList方法。

、、、

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

##2.接下来具体分析HomeFragment！
###2.1布局方式：
、、、

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_;
    }

- 协调者布局，1.Appbarlayout[子布局有1.ToolBar;2.Tablayout];2.Viewpager;3.FloatingActionButton.
###2.2逻辑介绍.
- new Instance方法。实现返回一个带有当前bundle数据的fragment实例。
、、、
    public static HomeFragment newInstance() {
        Bundle bundle = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

####在 onAttach方法中重要逻辑：
- **1.判断当前的上下文是不是OnOpenDrawerLayoutListener实例。如果是的话，则赋值给当前fragment声明的mOnOpenDrawerLayoutListener对象。在ToolBar的点击监听方法中，会根据当前的该接口不为空时响应它的onOpen方法，onOpen方法的逻辑在哪呢？在Mainactivity中如下：**

、、、

    //Mainactivity实现homrfragent里的接口,重写onOpen方法,在该方法中做判断,如果抽屉是关闭的,则打开
    @Override
    public void onOpen() {
        if (!dlRoot.isDrawerOpen(GravityCompat.START)) {
            dlRoot.openDrawer(GravityCompat.START);
        }
    }


- **2.new 泛型为Fragment的集合、这个集合用来存待会ViewPager中要存的Fragment对象**

、、、

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Mainactivity实现了OnOpenDrawerLayoutListener接口,Homefragment 依赖的activity为Mainactivity时
        if (context instanceof OnOpenDrawerLayoutListener) {
            mOnOpenDrawerLayoutListener = (OnOpenDrawerLayoutListener) context;
        }
        mFragments = new ArrayList<>();
    }
- **3.onDetach方法，在该方法中使接口为null，避免内存泄露吧**
- 
、、、

    @Override
    public void onDetach() {
        super.onDetach();
        mOnOpenDrawerLayoutListener = null;
    }
- **4.onLazyInitView；重写了SupportFragment的方法，在该方法中，调用BaseMVPCompatFragment中的Presenter的getTabList方法；在当前表示为：HomeMainContract.HomeMainPresenter对象**

、、、

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getTabList();
    }
- **4.initUI方法。重写了BaseCompatFragment中onViewCreated中创建的，主要是关于Toolbar的逻辑以及Appbar的逻辑以及FloatingButton的逻辑，业务很干练，代码贴一下**
- 
、、、

    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mToolbar.setTitle("首页");
        mToolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnOpenDrawerLayoutListener != null) {
                    //开启抽屉;接口回调
                    mOnOpenDrawerLayoutListener.onOpen();
                }
            }
        });
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    mFabDownload.show();
                } else {
                    mFabDownload.hide();
                }
            }
        });
        mFabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(BundleKeyConstant.ARG_KEY_WEB_VIEW_LOAD_TITLE, "YiZhiApp");
                bundle.putString(BundleKeyConstant.ARG_KEY_WEB_VIEW_LOAD_URL, "https://github.com/WenPingkk/YiZhiApp");
                startNewActivity(WebViewLoadActivity.class);
            }
        });
        //toolbar右侧menu的显示效果
        mToolbar.inflateMenu(R.menu.toolbar_menu);
        mToolbar.getMenu().findItem(R.id.night)
                .setChecked(SpUtils.getNightModel(mContext));
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.night:
        //夜间模式的实现原理:通过SpUtils的setNightMode方法,把当前的状态保存[true/false];接着调用BaseCompatActivity的reload方法,重新进入项目
        //此时会调用BaseCompatActivity中onCreate的方法下的init方法:setTheme(ThemeUtils.themeArr[SpUtils.getThemeIndex(this)][SpUtils.getNightModel(this) ? 1 : 0]);
        //实现切换到夜间模式
                        item.setChecked(!item.isChecked());
                        SpUtils.setNightModel(mContext, item.isChecked());
                        //重新设置夜间模式后要重新reload一次，才能显示效果。
                        ((BaseCompatActivity) mActivity).reload();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        
        ToolbarAnimManager.animIn(mContext, mToolbar);
    }
- **5.initPresenter方法；这个方法是在BaseMVPCompatFragment类重写了BaseCompatFragment的initData方法中的创建的抽象方法，返回当前的presenter对象。**

、、、

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return HomeMainPresenter.newInstance();
    }

- **6.showTabList方法；该方法源自HomeMainPresenter**
- HomeMainPresenter extends HomeMainContract.HomeMainPresenter，重写了HomeMainPresenter的getTabList方法，在该方法中用mIView对象调用的，其实就是HomeMainContract中的子接口IHomeMainView extends IBaseFragment中的方法

、、、

    @Override
    public void getTabList() {
        if (mIView == null || mIModel == null)
            return;

        mIView.showTabList(mIModel.getTabs());
    }

- HomeFragment的 showTabList方法如下

、、、

    @Override
    public void showTabList(String[] tabs) {
        //显示tab
        Logger.w(Arrays.toString(tabs));
        for (int i = 0; i < tabs.length; i++) {
            mTlTabs.addTab(mTlTabs.newTab().setText(tabs[i]));
            switch (i) {
                case TabFragmentIndex.TAB_ZHIHU_INDEX:
                    mFragments.add(ZhihuFragment.newInstance());
                    break;
                case TabFragmentIndex.TAB_WANGYI_INDEX:
                    mFragments.add(ZhihuFragment.newInstance());
                    break;
                case TabFragmentIndex.TAB_WEIXIN_INDEX:
                    mFragments.add(ZhihuFragment.newInstance());
                    break;

                default:
                    break;
            }
        }
        mVpFragment.setAdapter(new FragmentAdapter(getChildFragmentManager(), mFragments));
        mVpFragment.setCurrentItem(TabFragmentIndex.TAB_ZHIHU_INDEX);
        mTlTabs.setupWithViewPager(mVpFragment);
        mTlTabs.setVerticalScrollbarPosition(TabFragmentIndex.TAB_ZHIHU_INDEX);
        for (int i = 0; i < tabs.length; i++) {
            mTlTabs.getTabAt(i).setText(tabs[i]);
        }
    }

###MVP架构，的UML图是这样的：![](http://zjutkz.net/images/%E9%80%89%E6%8B%A9%E6%81%90%E6%83%A7%E7%97%87%E7%9A%84%E7%A6%8F%E9%9F%B3%EF%BC%81%E6%95%99%E4%BD%A0%E8%AE%A4%E6%B8%85MVC-MVP%E5%92%8CMVVM/mvp.png)

- View层和Presenter交互，和Model层解耦；Model层和Presenter层进行交互。

- 接下来主要分析：HomeMainPresenter!

、、、

	public class HomeMainPresenter extends HomeMainContract.HomeMainPresenter {
	
	    //返回HomeMainPresenter实例
	    @NonNull
	    public static HomeMainPresenter newInstance() {
	        return new HomeMainPresenter();
	    }
	    
	    //重写了HomeMainContract.HomeMainPresenter的方法
	    @Override
	    public void getTabList() {
	        if (mIView == null || mIModel == null)
	            return;
	        //HomeMainContract.HomeMainPresenter集成自BasePresenter;.当前泛型mIView对应的是IHomeMainView对象
	        //mIModel 对应的是IHomeMainModel
	        mIView.showTabList(mIModel.getTabs());
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



## MVP在homefragment中的应用。
- HomeFragment extends BaseMVPCompatFragment<HomeMainContract.HomeMainPresenter,
        HomeMainContract.IHomeMainModel> implements HomeMainContract.IHomeMainView；重写了一下几个方法
	
、、、

		//返回Presenter实例
	    @NonNull
	    @Override
	    public BasePresenter initPresenter() {
	        return null;
	    }
		//tablayout，viewpager：显示tab内容；这个方法在HomeMainPresenter重写的getTabList中被调用，由IHomeMainView接口对象实现
		//当前showTabList方法中的参数是在父接口中传入的；HomeMainPresenter绑定的是HomeMainModel；具体看ta的代码
	    @Override
	    public void showTabList(String[] tabs) {
	
	    }
		//返回当前布局
	    @Override
	    public int getLayoutId() {
	        return 0;
	    }
		//初始化当前布局
	    @Override
	    public void initUI(View view, @Nullable Bundle savedInstanceState) {
	
	    }

- **HomeMainModel代码**

、、、

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
- 1，关键的一环；在HomeFragment中继承了待泛型的HomeMainPresenter以及实现了IHomeMainView接口重写了所有的方法中，IHomeMainView重写了ta的showTablist方法，在该方法中传的参数是HomeMainModel类中重写父接口后创建的。
- 2，在showTabList方法中实现了tablayout，viewpager以及其绑定后，当前页面就存在了对吧
- 3，接着在HOmefragment中重写父类的onLazyInitView方法。在该方法要把页面显示出来：  mPresenter.getTabList();调用了HomeMainPresenter的getTablist方法，该方法内部实现就是这个    mIView.showTabList(mIModel.getTabs())！！是不是很熟悉，这就刚才重写的showTablist方法！！！


##HomeFragment继承结构分析：
- 1.HomeFragment extends BaseMVPCompatFragment<HomeMainContract.HomeMainPresenter,
        HomeMainContract.IHomeMainModel> implements HomeMainContract.IHomeMainView

- 2.BaseMVPCompatFragment<P extends BasePresenter, M extends IBaseModel> extends
        BaseCompatFragment implements IBaseFragment

- 3.BaseCompatFragment extends SupportFragment
##ZhiHuFragmetn继承结构分析：
- 1.ZhihuFragment extends BaseRecycleFragment<ZhihuContract.ZhihuPresenter, ZhihuContract.IZhihuModel> implements ZhihuContract.IZhihuView, BaseQuickAdapter.RequestLoadMoreListener
- 2.BaseRecycleFragment<P extends BasePresenter, M extends IBaseModel> extends BaseMVPCompatFragment<P, M>
- 3.BaseMVPCompatFragment<P extends BasePresenter, M extends IBaseModel> extends BaseCompatFragment implements IBaseFragment
- 4.BaseCompatFragment extends SupportFragment