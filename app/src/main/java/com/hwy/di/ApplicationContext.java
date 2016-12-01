package com.hwy.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/8 20:05
 * 修改人：heweiyun
 * 修改时间：2016/11/8 20:05
 * 修改备注：
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationContext {
}
