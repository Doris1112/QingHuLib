package com.qhsd.library.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.qhsd.library.R;
import com.qhsd.library.entity.api.Banner;

/**
 * @author Doris.
 * @date 2018/12/21.
 */

public class BaseBannerAdapter implements CBViewHolderCreator {

    @Override
    public Holder createHolder(View itemView) {
        return new BannerHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.lib_banner_image;
    }

    private class BannerHolder extends Holder<Banner> {

        private ImageView mImageView;

        BannerHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            mImageView = itemView.findViewById(R.id.lib_banner_image);
        }

        @Override
        public void updateUI(Banner data) {
            try {
                Glide.with(mImageView.getContext()).load(data.getImage()).into(mImageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
