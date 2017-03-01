package hwy.com.baselibrary.di.module;

import android.app.Application;
import android.content.Context;

import com.alipay.euler.andfix.patch.PatchManager;
import com.hwy.common.util.AppUtil;
import com.hwy.data.net.client.NetEasyGsonRetrofit;
import com.hwy.data.net.client.NetEasyStringRetrofit;
import com.hwy.data.net.client.ZhiHuRetrofit;
import com.hwy.data.net.service.NetEasyDetailService;
import com.hwy.data.net.service.NetEasyService;
import com.hwy.data.net.service.ZhiHuService;
import com.hwy.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/8 19:57
 * 修改人：heweiyun
 * 修改时间：2016/11/8 19:57
 * 修改备注：
 */

@Module
public class ApplicationModule {
    protected final Application mApplication;


    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication(){
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext(){
        return mApplication;
    }

    @Provides
    @Singleton
    ZhiHuService provideZhiHuService(ZhiHuRetrofit zhiHuRetrofit){
        return zhiHuRetrofit.get().create(ZhiHuService.class);
    }

    @Provides
    @Singleton
    NetEasyService provideNetEasyService(NetEasyGsonRetrofit netEasyRetrofit){
        return netEasyRetrofit.get().create(NetEasyService.class);
    }

    @Provides
    @Singleton
    NetEasyDetailService provideNetEasyDetailService(NetEasyStringRetrofit netEasyStringRetrofit){
        return netEasyStringRetrofit.get().create(NetEasyDetailService.class);
    }

    @Provides
    @Singleton
    PatchManager provoedPatchManager(){
        PatchManager patchManager = new PatchManager(mApplication);
        patchManager.init(AppUtil.getVersionName(mApplication));
        //加载已经添加到PatchManager的patch
        patchManager.loadPatch();
        return patchManager;
    }

}
