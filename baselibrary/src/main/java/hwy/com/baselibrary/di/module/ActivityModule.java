package hwy.com.baselibrary.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.hwy.di.ActivityContext;
import com.tbruyelle.rxpermissions.RxPermissions;

import dagger.Module;
import dagger.Provides;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/8 20:38
 * 修改人：heweiyun
 * 修改时间：2016/11/8 20:38
 * 修改备注：
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity){
        this.mActivity = activity;
    }

    public ActivityModule(Fragment fragment){
        this.mActivity = fragment.getActivity();
    }

    @Provides
    Activity provideActivity(){
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext(){
        return mActivity;
    }

    @Provides
    RxPermissions provideRxPermissions(){
        return new RxPermissions(mActivity);
    }

}
