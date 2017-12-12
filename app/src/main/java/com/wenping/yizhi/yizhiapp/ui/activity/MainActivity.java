package com.wenping.yizhi.yizhiapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.wansir.lib.logger.Logger;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.base.activity.BaseCompatActivity;
import com.wenping.yizhi.yizhiapp.helper.BottomNavigationViewHelper;
import com.wenping.yizhi.yizhiapp.rxbus.RxBus;
import com.wenping.yizhi.yizhiapp.ui.fragment.book.BookRootFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.gankio.GankIoRootFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.home.HomeRootFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.movie.MovieRootFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.personal.PersonalRootFragment;
import com.wenping.yizhi.yizhiapp.widget.MovingImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by wenping on 12/12/2017.
 */

public class MainActivity extends BaseCompatActivity {


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
            mFragments[SECOND] = GankIoRootFragment.newInstance();
            mFragments[THIRD] = MovieRootFragment.newInstance();
            mFragments[FOURTH] = BookRootFragment.newInstance();
            mFragments[FIFTH] = PersonalRootFragment.newInstance();
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

        //botomsheetbar
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

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
