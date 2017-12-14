package com.wenping.yizhi.yizhiapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.wansir.lib.logger.Logger;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.base.activity.BaseCompatActivity;
import com.wenping.yizhi.yizhiapp.helper.BottomNavigationViewHelper;
import com.wenping.yizhi.yizhiapp.rxbus.RxBus;
import com.wenping.yizhi.yizhiapp.ui.fragment.book.BookRootFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.gankio.GankIoRootFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.home.HomeRootFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.home.child.HomeFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.movie.MovieRootFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.personal.PersonalRootFragment;
import com.wenping.yizhi.yizhiapp.utils.SpUtils;
import com.wenping.yizhi.yizhiapp.widget.MovingImageView;
import com.wenping.yizhi.yizhiapp.widget.MovingViewAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 主页
 * Created by wenping on 12/12/2017.
 */

public class MainActivity extends BaseCompatActivity implements HomeFragment.OnOpenDrawerLayoutListener{

    @BindView(R.id.nv_menu)
    NavigationView nvMenu;
    @BindView(R.id.dl_root)
    DrawerLayout dlRoot;
    @BindView(R.id.bviv_bar)
    BottomNavigationView bottomNavigationView;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    public static final int FIFTH = 4;

    private SupportFragment[] mFragments = new SupportFragment[5];

    private MovingImageView mivMenu;
    private CircleImageView civHead;

    //再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    //注册rxBus
    @Override
    protected void initData() {
        super.initData();
        Logger.e("RxBus.get().register(this)");
        RxBus.get().register(this);
    }

    //反注册rxBus
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.e("RxBus.get().unRegister(this)");
        RxBus.get().unRegister(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //如果是第一进入页面
        if (savedInstanceState == null) {
            mFragments[FIRST] = HomeRootFragment.newInstance();
            mFragments[SECOND] = HomeRootFragment.newInstance();
            mFragments[THIRD] = HomeRootFragment.newInstance();
            mFragments[FOURTH] = HomeRootFragment.newInstance();
            mFragments[FIFTH] = HomeRootFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH],
                    mFragments[FIFTH]);

        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()
            // 自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(HomeRootFragment.class);
            mFragments[SECOND] = findFragment(GankIoRootFragment.class);
            mFragments[THIRD] = findFragment(MovieRootFragment.class);
            mFragments[FOURTH] = findFragment(BookRootFragment.class);
            mFragments[FIFTH] = findFragment(PersonalRootFragment.class);
        }
        //MovingImageView的相关操作
        mivMenu = (MovingImageView) nvMenu.getHeaderView(0).findViewById(R.id.miv_menu);
        civHead = (CircleImageView) nvMenu.getHeaderView(0).findViewById(R.id.civ_head);
        // 此处将实际应用中替换成服务器中拉取的图片
        // TODO: 2017/12/14 代码待完善
        //点击头像的操作:底部tab跳转到[个人]
        civHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlRoot.closeDrawer(GravityCompat.START);
                bottomNavigationView.setSelectedItemId(R.id.menu_item_personal);
            }
        });

        //botomsheetbar 的点击效果
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_home:
                        showHideFragment(mFragments[FIRST]);
                        break;
                    case R.id.menu_item_gank_io:
                        showHideFragment(mFragments[SECOND]);
                        break;
                    case R.id.menu_item_movie:
                        showHideFragment(mFragments[THIRD]);
                        break;
                    case R.id.menu_item_book:
                        showHideFragment(mFragments[FOURTH]);
                        break;
                    case R.id.menu_item_personal:
                        showHideFragment(mFragments[FIFTH]);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        
        nvMenu.getMenu().findItem(R.id.item_model).setTitle(SpUtils.getNightModel(mContext)?"夜间模式":"日间模式");
        //左侧抽屉对应的item点击效果
        nvMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.group_item_github:

                        break;
                    case R.id.group_item_more:

                        break;
                    case R.id.group_item_qr_code:

                        break;
                    case R.id.group_item_share_project:

                        break;
                        default:
                            break;
                }
                return false;
            }
        });

        dlRoot.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mivMenu.pauseMoving();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (mivMenu.getMovingState() == MovingViewAnimator.MovingState.stop) {
                    mivMenu.startMoving();
                } else if (mivMenu.getMovingState() == MovingViewAnimator.MovingState.pause) {
                    mivMenu.resumeMoving();
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mivMenu.stopMoving();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if (mivMenu.getMovingState() == MovingViewAnimator.MovingState.stop) {
                    mivMenu.startMoving();
                } else if (mivMenu.getMovingState() == MovingViewAnimator.MovingState.pause) {
                    mivMenu.resumeMoving();
                }
            }
        });
    }

    //获取当前layouty的布局ID,用于设置当前布局
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    //实现homrfragent里的接口,重写onOpen方法,在改方法中做判断,如果抽屉是关闭的,则打开
    @Override
    public void onOpen() {
        if (!dlRoot.isDrawerOpen(GravityCompat.START)) {
            dlRoot.openDrawer(GravityCompat.START);
        }
    }
}
