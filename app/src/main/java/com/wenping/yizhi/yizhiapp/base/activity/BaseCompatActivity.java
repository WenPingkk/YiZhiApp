package com.wenping.yizhi.yizhiapp.base.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.global.GlobalApplication;
import com.wenping.yizhi.yizhiapp.manager.AppManager;
import com.wenping.yizhi.yizhiapp.utils.SpUtils;
import com.wenping.yizhi.yizhiapp.utils.StatusBarUtils;
import com.wenping.yizhi.yizhiapp.utils.ThemeUtils;
import com.wenping.yizhi.yizhiapp.widget.WaitPorgressDialog;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by YinZeTong on 2017/12/11.
 */

public abstract class BaseCompatActivity extends SupportActivity {
    protected GlobalApplication mApplication;
    protected WaitPorgressDialog mWaitPorgressDialog;
    //全局上下文对象
    protected Context mContext;
    protected boolean isTransAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        //fragment切换使用默认Vertical动画
        return new DefaultVerticalAnimator();
    }

    private void init(Bundle savedInstanceState) {
        setTheme(ThemeUtils.themeArr[SpUtils.getThemeIndex(this)][
                SpUtils.getNightModel(this) ? 1 : 0]);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        StatusBarUtils.setTransparent(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initData();
        initView(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    /**
     * 初始化数据
     * <p>
     * 子类可以复写此方法初始化子类数据
     */
    protected void initData() {
        //修改了这里
//        mContext = AppUtils.getContext();
        mContext = this;
        mApplication = (GlobalApplication) getApplication();
        mWaitPorgressDialog = new WaitPorgressDialog(this);
        isTransAnim = true;
    }

    /**
     * 初始化view
     * <p>
     * 子类实现 控件绑定、视图初始化等内容
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 获取当前layouty的布局ID,用于设置当前布局
     * <p>
     * 交由子类实现
     *
     * @return layout Id
     */
    protected abstract int getLayoutId();

    /**
     * 显示提示框
     *
     * @param msg 提示框内容字符串
     */
    protected void showProgressDialog(String msg) {
        mWaitPorgressDialog.setMessage(msg);
        mWaitPorgressDialog.show();
    }

    /**
     * 隐藏提示框
     */
    protected void hideProgressDialog() {
        if (mWaitPorgressDialog != null) {
            mWaitPorgressDialog.dismiss();
        }
    }

    /**
     * [页面跳转]
     *
     * @param clz 要跳转的Activity
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
        if (isTransAnim) {
            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim
                    .activity_start_zoom_out);
        }
    }

    /**
     * [页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param intent intent
     */
    public void startActivity(Class<?> clz, Intent intent) {
        intent.setClass(this, clz);
        startActivity(intent);
        if (isTransAnim) {
            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim
                    .activity_start_zoom_out);
        }
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param bundle bundel数据
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (isTransAnim) {
            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim
                    .activity_start_zoom_out);
        }
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param clz         要跳转的Activity
     * @param bundle      bundel数据
     * @param requestCode requestCode
     */
    public void startActivityForResult(Class<?> clz, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        if (isTransAnim){
            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim
                    .activity_start_zoom_out);
        }

    }

    @Override
    public void finish() {
        super.finish();
        if (isTransAnim) {
            overridePendingTransition(R.anim.activity_finish_trans_in, R.anim
                    .activity_finish_trans_out);
        }
    }

    /**
     * 隐藏键盘
     *
     * @return 隐藏键盘结果
     * <p>
     * true:隐藏成功
     * <p>
     * false:隐藏失败
     */
    protected boolean hiddenKeyboard() {
        //点击空白位置 隐藏软键盘
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService
                (INPUT_METHOD_SERVICE);
        return mInputMethodManager.hideSoftInputFromWindow(this
                .getCurrentFocus().getWindowToken(), 0);
    }

    protected void initTitleBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }

    /**
     * 是否使用overridePendingTransition过度动画
     * @return 是否使用overridePendingTransition过度动画，默认使用
     */
    protected boolean isTransAnim() {
        return isTransAnim;
    }

    /**
     * 设置是否使用overridePendingTransition过度动画
     */
    protected void setIsTransAnim(boolean b){
        isTransAnim = b;
    }
}
