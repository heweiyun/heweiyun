package hwy.com.baselibrary.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/9 16:22
 * 修改人：heweiyun
 * 修改时间：2016/11/9 16:22
 * 修改备注：
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
