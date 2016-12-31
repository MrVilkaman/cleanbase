package com.github.mrvilkaman.presentationlayer.resolution;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

public interface ImageLoader {

	Builder loadUrl(@NonNull String url);

	Builder loadFile(@NonNull String path);

	interface Builder {

		Builder size(int width, int height);

		Builder holder(@DrawableRes int holderResId);

		Builder error(@DrawableRes int errorResId);

		Builder height(int dimension);

		Builder width(int dimension);

		void into(@NonNull ImageView target);
	}
}
