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
	@Deprecated
	public void showFromFile(@NonNull String path, @NonNull ImageView target) {
		picasso.load(new File(path))
				.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
				.into(target);
	}

	@Override
	@Deprecated
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

	@Override
	public Builder loadUrl(String url) {
		RequestCreator load = picasso.load(url);
		return new MyBuilder(load);
	}

	@Override
	public Builder loadFile(String path) {
		RequestCreator load = picasso.load(new File(path))
				.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
		return new MyBuilder(load);
	}

	private static class MyBuilder implements Builder {

		private final RequestCreator creator;

		public MyBuilder(RequestCreator creator) {
			this.creator = creator;
		}

		@Override
		public Builder size(int width, int height) {
			creator.centerCrop()
					.resize(width, height)
					.onlyScaleDown();
			return this;
		}

		@Override
		public Builder holder(@DrawableRes int holderResId) {
			creator.placeholder(holderResId);
			return this;
		}

		@Override
		public Builder error(@DrawableRes int errorResId) {
			creator.error(errorResId);
			return this;
		}

		@Override
		public Builder height(int dimension) {
			creator.resize(0, dimension)
					.onlyScaleDown();
			return this;
		}

		@Override
		public Builder width(int dimension) {
			creator.resize(dimension, 0)
					.onlyScaleDown();
			return this;
		}

		@Override
		public void into(ImageView target) {
			creator.into(target);
		}
	}
}
