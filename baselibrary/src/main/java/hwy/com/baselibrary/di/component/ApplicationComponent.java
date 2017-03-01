package hwy.com.baselibrary.di.component;

import android.app.Application;
import android.content.Context;

import com.alipay.euler.andfix.patch.PatchManager;
import com.hwy.data.net.service.NetEasyDetailService;
import com.hwy.data.net.service.NetEasyService;
import com.hwy.data.net.service.ZhiHuService;
import com.hwy.di.ApplicationContext;
import com.hwy.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/8 19:59
 * 修改人：heweiyun
 * 修改时间：2016/11/8 19:59
 * 修改备注：
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    ZhiHuService zhiHuService();

    NetEasyService netEasyService();

    NetEasyDetailService netEasyDetailService();

    PatchManager patchManager();
}
