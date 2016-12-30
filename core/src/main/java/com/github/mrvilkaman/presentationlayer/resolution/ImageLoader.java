package com.github.mrvilkaman.presentationlayer.resolution;

import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

public interface ImageLoader {
	void showFromFile(String path, ImageView target);

	void load(Uri uri, int width, int height, @DrawableRes int placeholderResId,
			  @DrawableRes int errorResId, ImageView target);
}
