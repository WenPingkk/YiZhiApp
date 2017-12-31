package com.wenping.yizhi.yizhiapp.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import com.wansir.lib.logger.Logger;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.base.activity.BaseCompatActivity;
import com.wenping.yizhi.yizhiapp.constant.BundleKeyConstant;
import com.wenping.yizhi.yizhiapp.constant.HeadConstant;
import com.wenping.yizhi.yizhiapp.helper.BottomNavigationViewHelper;
import com.wenping.yizhi.yizhiapp.model.bean.rxbus.RxEventHeadBean;
import com.wenping.yizhi.yizhiapp.rxbus.RxBus;
import com.wenping.yizhi.yizhiapp.rxbus.Subscribe;
import com.wenping.yizhi.yizhiapp.ui.activity.detail.WebViewLoadActivity;
import com.wenping.yizhi.yizhiapp.ui.fragment.book.BookRootFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.gankio.GankIoRootFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.home.HomeRootFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.home.child.HomeFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.movie.MovieRootFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.personal.PersonalRootFragment;
import com.wenping.yizhi.yizhiapp.utils.AppUtils;
import com.wenping.yizhi.yizhiapp.utils.FileUtils;
import com.wenping.yizhi.yizhiapp.utils.SpUtils;
import com.wenping.yizhi.yizhiapp.utils.ToastUtils;
import com.wenping.yizhi.yizhiapp.widget.MovingImageView;
import com.wenping.yizhi.yizhiapp.widget.MovingViewAnimator;

import java.io.File;

import butterknife.BindView;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

import static com.wenping.yizhi.yizhiapp.constant.RxBusCode.RX_BUS_CODE_HEAD_IMAGE_URI;

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

    //获取当前layouty的布局ID,用于设置当前布局
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

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

            //加载多个同级根Fragment,类似Wechat, QQ主页的场景
            loadMultipleRootFragment(
                    R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH],
                    mFragments[FIFTH]);
        } else {
            /**
             * findFragment 方法
             * 获取栈内的fragment对象
             */

            //public <T extends ISupportFragment> T findFragment(Class<T> fragmentClass) {
            //    return SupportHelper.findFragment(getSupportFragmentManager(), fragmentClass);
            //}

            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()
            // 自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(HomeRootFragment.class);
            mFragments[SECOND] = findFragment(GankIoRootFragment.class);
            mFragments[THIRD] = findFragment(MovieRootFragment.class);
            mFragments[FOURTH] = findFragment(BookRootFragment.class);
            mFragments[FIFTH] = findFragment(PersonalRootFragment.class);
        }
        //左侧抽屉的相关操作
        //MovingImageView的相关操作
        mivMenu = (MovingImageView) nvMenu.getHeaderView(0).findViewById(R.id.miv_menu);
        civHead = (CircleImageView) nvMenu.getHeaderView(0).findViewById(R.id.civ_head);
        // 此处将实际应用中替换成服务器中拉取的图片
        Uri headUri = Uri.fromFile(new File(getCacheDir(), HeadConstant.HEAD_IMAGE_NAME+".jpg"));
        if (headUri != null) {
            String cropImagePath = FileUtils.getRealFilePathFromUri(AppUtils.getContext(),headUri);
            Bitmap bitmap = BitmapFactory.decodeFile(cropImagePath);
            if (bitmap != null) {
                civHead.setImageBitmap(bitmap);
            }
        }
        //点击头像的操作:底部tab跳转到[个人]
        civHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlRoot.closeDrawer(GravityCompat.START);
                bottomNavigationView.setSelectedItemId(R.id.menu_item_personal);
            }
        });

        //botomsheetbar 的点击效果

        /**
         * show一个Fragment,hide其他同栈所有Fragment
         * 使用该方法时，要确保同级栈内无多余的Fragment,(只有通过loadMultipleRootFragment()载入的Fragment)
         * <p>
         * 建议使用更明确的{@link #showHideFragment(ISupportFragment, ISupportFragment)}
         *
         * @param showFragment 需要show的Fragment
         */
//        public void showHideFragment(ISupportFragment showFragment) {
//            mDelegate.showHideFragment(showFragment);
//        }

        /**
         * show一个Fragment,hide一个Fragment ; 主要用于类似微信主页那种 切换tab的情况
         */
//        public void showHideFragment(ISupportFragment showFragment, ISupportFragment hideFragment) {
//            mDelegate.showHideFragment(showFragment, hideFragment);
//        }

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
                        Bundle bundle = new Bundle();
                        bundle.putString(BundleKeyConstant.ARG_KEY_WEB_VIEW_LOAD_URL,"YiZhiApp");
                        bundle.putString(BundleKeyConstant.ARG_KEY_WEB_VIEW_LOAD_URL
                        ,"https://github.com/WenPingkk/YiZhiApp");
                        startActivity(WebViewLoadActivity.class,bundle);
                        break;
                    case R.id.group_item_more:
                        Bundle bundle12 = new Bundle();
                        bundle12.putString(BundleKeyConstant.ARG_KEY_WEB_VIEW_LOAD_TITLE,"WenPing");
                        bundle12.putString(BundleKeyConstant.ARG_KEY_WEB_VIEW_LOAD_URL,"http://blog.csdn.net/topwilling");
                        startActivity(WebViewLoadActivity.class,bundle12);
                        break;
                    case R.id.group_item_qr_code:
                        //显示二维码并分享
                        break;
                    case R.id.group_item_share_project:
                        showShare();
                        break;

                    case R.id.item_model:
                        //日间模式
                        SpUtils.setNightModel(mContext,!SpUtils.getNightModel(mContext));
                        //重新加载一次
                        MainActivity.this.reload();
                        break;
                    case R.id.item_about:
                        //关于
                        ToastUtils.showToast("待完善");
//                        startActivity(AboutA);
                        break;
                        default:
                            break;
                }
                item.setCheckable(false);
                dlRoot.closeDrawer(GravityCompat.START);
                //return true 对应的意思是:
                return true;
            }
        });

        /**
         * drawerLayout的监听器
         */
        dlRoot.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //movingImage 停止移动
                mivMenu.pauseMoving();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //如果movigImage处于静止状态则开始移动
                if (mivMenu.getMovingState() == MovingViewAnimator.MovingState.stop) {
                    mivMenu.startMoving();
                    //如果movingImageView处于暂停状态则 :恢复移动
                } else if (mivMenu.getMovingState() == MovingViewAnimator.MovingState.pause) {
                    mivMenu.resumeMoving();
                }
            }

            /**
             * 抽屉关闭则movingImage停止运动
             * @param drawerView
             */
            @Override
            public void onDrawerClosed(View drawerView) {
                mivMenu.stopMoving();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if (mivMenu.getMovingState() == MovingViewAnimator.MovingState.stop) {
                    //开始移动
                    mivMenu.startMoving();
                } else if (mivMenu.getMovingState() == MovingViewAnimator.MovingState.pause) {
                    //恢复移动
                    mivMenu.resumeMoving();
                }
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        //要注释掉super方法,防止调用父类的逻辑
        //super.onBackPressedSupport();

        if (dlRoot.isDrawerOpen(GravityCompat.START)) {
            dlRoot.closeDrawer(GravityCompat.START);
            return;
        }
        //如果当前存在的fragment>1,当前fragment出栈
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                setIsTransAnim(false);
                finish();
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                ToastUtils.showToast(R.string.press_again);
            }
        }
    }


    private void showShare() {
        //微信朋友圈和收藏分享功能未完善
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("YiZhiApp");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("https://github.com/WenPingkk/YiZhiApp");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("这是仿一之App");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("https://github.com/WenPingkk/YiZhiApp");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("仿一之App");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("https://github.com/WenPingkk/YiZhiApp");

        // 启动分享GUI
        oks.show(this);
    }

    //实现homrfragent里的接口,重写onOpen方法,在改方法中做判断,如果抽屉是关闭的,则打开
    @Override
    public void onOpen() {
        if (!dlRoot.isDrawerOpen(GravityCompat.START)) {
            dlRoot.openDrawer(GravityCompat.START);
        }
    }
    /**
     * RxBus接收图片Uri
     *
     * @param bean RxEventHeadBean
     */
    @Subscribe(code = RX_BUS_CODE_HEAD_IMAGE_URI)
    public void rxBusEvent(RxEventHeadBean bean) {
        Uri uri = bean.getUri();
        if (uri == null) {
            return;
        }
        String cropImagePath = FileUtils.getRealFilePathFromUri(AppUtils.getContext(), uri);
        Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
        if (bitMap != null) {
            civHead.setImageBitmap(bitMap);
        }
    }

}
