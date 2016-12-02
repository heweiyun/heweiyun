package com.hwy.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwy.di.GetComponent;

import butterknife.ButterKnife;

/**
 * 功能描述：所有Fragment的基类
 * 创建人：heweiyun
 * 创建时间：2016/11/8 19:51
 * 修改人：heweiyun
 * 修改时间：2016/11/8 19:51
 * 修改备注：
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDagger();
        attatchPresent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        int contentResId = getContentResId();
        if (contentResId != 0){
            View contentView = inflater.inflate(contentResId,null);
            ButterKnife.bind(this,contentView);
            initViews();
            initDatas();
            initEvents();
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachPresent();
    }

    protected abstract int getContentResId();

    /**
     * 注解dagger，如果没有不填写
     */
    protected abstract void injectDagger();

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


    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((GetComponent<C>) getActivity()).getComponent());
    }
}
