package com.hwy.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hwy.di.GetComponent;

import butterknife.ButterKnife;

/**
 * 功能描述：所有Activity的基类
 * 创建人：heweiyun
 * 创建时间：2016/11/8 19:51
 * 修改人：heweiyun
 * 修改时间：2016/11/8 19:51
 * 修改备注：
 */

public abstract class BaseActivity<C>  extends AppCompatActivity implements GetComponent<C>{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int contentResId = getContentResId();
        if (contentResId != 0){
            setContentView(getContentResId());
            ButterKnife.bind(this);
        }
        injectDagger();
        attatchPresent();
        initViews();
        initDatas();
        initEvents();
    }

    protected abstract int getContentResId();

    /**
     * 注解dagger，如果没有不填写
     */
    protected abstract void injectDagger();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachPresent();
    }

    /**
     * 初始化控件
     */
    protected abstract void initViews();

    /**
     *初始化数据
     */
    protected abstract void initDatas();

    /**
     *初始化事件
     */
    protected abstract void initEvents();

    /**
     * 绑定Present
     */
    protected abstract void attatchPresent();

    /**
     * 解绑Present
     */
    protected abstract void detachPresent();

}
