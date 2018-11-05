package com.tuann.mvvm.presentation.common.binding

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.tuann.mvvm.R

@BindingAdapter("image_url")
fun setImageFromImageUrl(imageView: ImageView, imageUrl: String) {
    if (TextUtils.isEmpty(imageUrl)) {
        return
    }
    Glide
            .with(imageView.context)
            .load(imageUrl)
            .apply {
                RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .dontAnimate()
                        .transform(CircleCrop())
            }
            .into(imageView)
}