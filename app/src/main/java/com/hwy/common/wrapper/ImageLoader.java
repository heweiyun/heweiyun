package com.hwy.common.wrapper;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/28 14:53
 * 修改人：heweiyun
 * 修改时间：2016/11/28 14:53
 * 修改备注：
 */

public class ImageLoader {

    public static void load(Context context, Object source, ImageView view) {
        Glide.with(context)
                .load(source)
                .centerCrop()
                .into(view);
    }


    public static void load(Fragment fragment, Object source, ImageView imageView){
        Glide.with(fragment)
                .load(source)
                .centerCrop()
                .into(imageView);
    }

    public static void load(Activity activity,Object source,ImageView imageView){
        Glide.with(activity)
                .load(source)
                .centerCrop()
                .into(imageView);
    }


    public static void load(Object source, ImageView view) {
        Glide.with(view.getContext())
                .load(source)
                .centerCrop()
                .into(view);
    }




}
