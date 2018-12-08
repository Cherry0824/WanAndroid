package com.rxjava.net.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;


/**
 * * .with() 图片加载的环境：1,Context对象。2,Activity对象。3,FragmentActivity对象。4,Fragment对象
 * .load() 加载资源：1,drawable资源。2,本地File文件。3,uri。4,网络图片url。5,byte数组（可以直接加载GIF图片）
 * .placeholder() 图片占位符
 * .error() 图片加载失败时显示
 * .crossFade() 显示图片时执行淡入淡出的动画默认300ms
 * .dontAnimate() 不执行显示图片时的动画
 * .override() 设置图片的大小
 * .centerCrop() 和 fitCenter() 图片的显示方式
 * .animate() view动画 2个重构方法
 * .transform() bitmap转换
 * .bitmapTransform() bitmap转换。比如旋转，放大缩小，高斯模糊等（当用了转换后你就不能使用.centerCrop()或.fitCenter()了。）
 * .priority(Priority.HIGH) 当前线程的优先级
 * .signature(new StringSignature(“ssss”))
 * .thumbnail(0.1f) 缩略图，3个重构方法：优先显示原始图片的百分比(10%)
 * .listener() 异常监听
 * .into() 图片加载完成后进行的处理：1,ImageView对象。2,宽高值。3,Target对象
 * <p>
 * <p>
 * Created by  on 2017/5/17.
 */
public class ImageUtils {


    //    public static int placeholder = R.drawable.icon_loading;
//    public static int error = R.drawable.icon_loading_error;
    public static int duration = 500;
    public static int radius = 29;

    public static void show(Context context, String url, ImageView imageView, int placeholder, int error) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)
                .error(error)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).transition(new DrawableTransitionOptions().crossFade(duration)).apply(options).into(imageView);
    }

    public static void show(Context context, int resources, ImageView imageView) {
        Glide.with(context).load(resources).into(imageView);
    }

    public static void show(Context context, String path, ImageView imageView) {
        Glide.with(context).load(path).transition(new DrawableTransitionOptions().crossFade(duration)).into(imageView);
    }

    public static void showMaxImage(Context context, String path, SimpleTarget<File> simpleTarget) {
        Glide.with(context).asFile().load(path).into(simpleTarget);
    }

    public static void showBitmap(Context context, String path, SimpleTarget<Bitmap> simpleTarget) {
        Glide.with(context).asBitmap().load(path).into(simpleTarget);
    }

    public static void showGif(Context context, String path, ImageView imageView) {
        Glide.with(context).asGif().load(path).into(imageView);
    }

    public static void showGif(Context context, int drawable, ImageView imageView) {
        Glide.with(context).asGif().load(drawable).into(imageView);
    }



}
