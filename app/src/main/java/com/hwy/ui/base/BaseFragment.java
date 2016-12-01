package com.hwy.ui.base;

import android.support.v4.app.Fragment;

import com.hwy.di.GetComponent;

/**
 * 功能描述：所有Fragment的基类
 * 创建人：heweiyun
 * 创建时间：2016/11/8 19:51
 * 修改人：heweiyun
 * 修改时间：2016/11/8 19:51
 * 修改备注：
 */

public class BaseFragment extends Fragment {


    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((GetComponent<C>) getActivity()).getComponent());
    }
}
