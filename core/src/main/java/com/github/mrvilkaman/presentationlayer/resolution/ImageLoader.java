package com.github.mrvilkaman.presentationlayer.resolution;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

public interface ImageLoader {
	void showFromFile(String path, ImageView target);

	void load(String uri, int width, int height, @DrawableRes int placeholderResId,
			  @DrawableRes int errorResId, ImageView target);
}
