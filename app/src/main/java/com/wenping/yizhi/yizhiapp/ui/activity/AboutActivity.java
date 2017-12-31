package com.wenping.yizhi.yizhiapp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wansir.lib.logger.Logger;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.base.activity.BaseCompatActivity;
import com.wenping.yizhi.yizhiapp.utils.AppUtils;
import com.wenping.yizhi.yizhiapp.utils.ResourcesUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author WenPing
 * @date 12/31/2017
 */

public class AboutActivity extends BaseCompatActivity {


    @BindView(R.id.iv_header)
    ImageView mIvHeader;
    @BindView(R.id.tv_version_code)
    TextView mTvVersionCode;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbarlayout_about_fragment)
    AppBarLayout mAppbarlayoutAboutFragment;
    @BindView(R.id.tv_card_about_fragment)
    TextView mTvCardAboutFragment;
    @BindView(R.id.civ_author_head)
    CircleImageView mCivAuthorHead;
    @BindView(R.id.cv_author)
    CardView mCvAuthor;

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar,"关于");
        mToolbar.setTitleTextColor(ResourcesUtils.getColor(R.color.md_white));
        Logger.e("versonCode:"+mTvVersionCode);
        mTvVersionCode.setText(AppUtils.getAppVersionName(this));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    /**
     * vardvie点击效果
     * @param view
     */
    @OnClick(R.id.cv_author)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_author:
                Intent intent = new Intent();
                intent.setData(Uri.parse("https://github.com/WenPingkk/YiZhiApp"));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent); //启动浏览器
                break;
            default:
                break;
        }
    }

}
