package com.hwy.di.component;

import com.hwy.di.PerActivity;
import com.hwy.di.module.ActivityModule;
import com.hwy.ui.module.zhihu.ZhiHuDescribleActivity;

import dagger.Component;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/12/2 11:03
 * 修改人：heweiyun
 * 修改时间：2016/12/2 11:03
 * 修改备注：
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ZhiHuDescribleComponent {
    void inject(ZhiHuDescribleActivity zhiHuDescribleActivity);
}
