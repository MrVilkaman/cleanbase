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
	public Builder load(@NonNull String url) {
		RequestCreator load = picasso.load(url);
		return new MyBuilder(load);
	}

	@Override
	public Builder loadFile(@NonNull String path) {
		RequestCreator load = picasso.load(new File(path))
				.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
		return new MyBuilder(load);
	}

	private static class MyBuilder implements Builder {

		private RequestCreator creator;

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
		public void into(@NonNull ImageView target) {
			creator.into(target);
		}
	}
}
