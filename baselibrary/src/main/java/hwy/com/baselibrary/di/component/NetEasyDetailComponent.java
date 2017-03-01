package hwy.com.baselibrary.di.component;

import com.hwy.di.PerActivity;
import com.hwy.di.module.ActivityModule;
import com.hwy.ui.module.neteasy.NetEasyDetailActivity;

import dagger.Component;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/12/2 11:11
 * 修改人：heweiyun
 * 修改时间：2016/12/2 11:11
 * 修改备注：
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface NetEasyDetailComponent {
    void inject(NetEasyDetailActivity netEasyDetailActivity);
}
