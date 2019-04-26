package com.qhsd.library.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

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
        try {
            Glide.with(getContext()).load(path).into(this);
        } catch (Exception e){
            picassoLoadImage(path);
        }
    }

    public void setImage(String path, RequestOptions options) {
        try {
            Glide.with(getContext()).load(path).apply(options).into(this);
        } catch (Exception e){
            picassoLoadImage(path, options.getPlaceholderId(), options.getErrorId());
        }
    }

    public void picassoLoadImage(String path) {
        try {
            Picasso.get().load(path).into(this);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void picassoLoadImage(String path, int placeholderRes, int errorRes) {
        try {
            Picasso.get().load(path)
                    .placeholder(placeholderRes)
                    .error(errorRes)
                    .into(this);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
