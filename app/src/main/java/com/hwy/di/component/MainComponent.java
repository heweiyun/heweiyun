package com.hwy.di.component;

import com.hwy.di.PerActivity;
import com.hwy.di.module.ActivityModule;
import com.hwy.ui.module.neteasy.NetEasyDetailActivity;
import com.hwy.ui.module.neteasy.NetEasyFragment;
import com.hwy.ui.module.zhihu.ZhiHuDescribleActivity;
import com.hwy.ui.module.zhihu.ZhiHuFragment;

import dagger.Component;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/28 16:23
 * 修改人：heweiyun
 * 修改时间：2016/11/28 16:23
 * 修改备注：
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface MainComponent extends ActivityComponent{
    void inject(ZhiHuFragment zhiHuFragment);
    void inject(NetEasyFragment netEasyFragment);
    void inject(ZhiHuDescribleActivity zhiHuDescribleActivity);
    void inject(NetEasyDetailActivity netEasyDetailActivity);
}
