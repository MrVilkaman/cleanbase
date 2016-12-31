package com.github.mrvilkaman.presentationlayer.resolution;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

public interface ImageLoader {
	@Deprecated
	void showFromFile(String path, ImageView target);

	@Deprecated
	void load(String uri, int width, int height, @DrawableRes int placeholderResId,
			  @DrawableRes int errorResId, ImageView target);


	Builder loadUrl(String url);

	Builder loadFile(String path);

	interface Builder {

		Builder size(int width, int height);

		Builder holder(@DrawableRes int holderResId);

		Builder error(@DrawableRes int errorResId);

		void into(ImageView target);

		Builder height(int dimension);

		Builder width(int dimension);
	}
}
