package hwy.com.baselibrary.di.component;

import android.app.Activity;

import com.hwy.di.PerActivity;
import com.hwy.di.module.ActivityModule;
import com.tbruyelle.rxpermissions.RxPermissions;

import dagger.Component;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/8 20:41
 * 修改人：heweiyun
 * 修改时间：2016/11/8 20:41
 * 修改备注：
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {

    Activity activity();

    RxPermissions rxPermissions();
}
