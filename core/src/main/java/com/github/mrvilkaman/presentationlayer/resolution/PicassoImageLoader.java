package com.github.mrvilkaman.presentationlayer.resolution;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

public class PicassoImageLoader implements ImageLoader {
	private final Picasso picasso;

	public PicassoImageLoader(Picasso picasso) {
		this.picasso = picasso;
	}

	@Override
	public void showFromFile(@NonNull String path, @NonNull ImageView target) {
		picasso.load(new File(path))
				.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
				.into(target);
	}

	@Override
	public void load(String uri, int width, int height, @DrawableRes int placeholderResId,
					 @DrawableRes int errorResId, ImageView target) {

		RequestCreator load;
		if (uri.startsWith("http://")) {
			load = picasso.load(uri);
		} else
//		if (uri.startsWith("file"))
		{
			load = picasso.load(new File(uri))
					.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
		}

		if (0 < width && 0 < height) {
			load = load.centerCrop();
		}
		if (0 != width || 0 != height) {
			load = load.resize(width, height)
					.onlyScaleDown();
		}
		if (0 < placeholderResId) {
			load = load.placeholder(placeholderResId);
		}
		if (0 < errorResId) {
			load = load.error(errorResId);
		}


		load.into(target);
	}
}
