package com.rxjava.net.comm;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Lenovo on 2018/7/4.
 */

public class BannerImageLoader extends ImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).transition(new DrawableTransitionOptions().crossFade(500)).into(imageView);
    }
}
