package com.hwy.common.wrapper;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hwy.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/28 14:53
 * 修改人：heweiyun
 * 修改时间：2016/11/28 14:53
 * 修改备注：
 */

public class ImageLoader {

    /**
     * Load image from source and set it into the imageView.
     * @param context context.
     * @param source could be Uri/String/File/ResourceId.
     * @param view the imageView.
     */
    public static void load(Context context, Object source, ImageView view) {
        Glide.with(context)
                .load(source)
                .centerCrop()
                .into(view);
    }

    public static void load(Object source, ImageView view) {
        Glide.with(view.getContext())
                .load(source)
                .centerCrop()
                .into(view);
    }

    public static void loadWithCircle(Context context, Object source, ImageView view) {
        Glide.with(context)
                .load(source)
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(R.drawable.zhihu)
                .into(view);
    }

    public static void loadWithCircle(Object source, ImageView view) {
        Glide.with(view.getContext())
                .load(source)
                .bitmapTransform(new CropCircleTransformation(view.getContext()))
                .into(view);
    }

}
