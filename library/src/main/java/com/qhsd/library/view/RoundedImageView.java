package com.qhsd.library.view;

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author Doris.
 * @date 2018/12/14.
 */
public class RoundedImageView extends com.makeramen.roundedimageview.RoundedImageView {

    public RoundedImageView(Context context) {
        super(context);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImage(String path) {
        Glide.with(getContext()).load(path).into(this);
    }

    public void setImage(String path, RequestOptions options) {
        Glide.with(getContext()).load(path).apply(options).into(this);
    }

}
