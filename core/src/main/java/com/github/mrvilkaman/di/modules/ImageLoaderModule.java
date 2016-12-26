package com.github.mrvilkaman.di.modules;


import android.content.Context;
import android.support.annotation.NonNull;

import com.github.mrvilkaman.presentationlayer.app.CleanBaseSettings;
import com.github.mrvilkaman.presentationlayer.resolution.ImageLoader;
import com.github.mrvilkaman.presentationlayer.resolution.PicassoImageLoader;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
@Singleton
public class ImageLoaderModule {

	@Provides
	@NonNull
	@Singleton
	public Picasso providePicasso(@NonNull Context context, @NonNull OkHttpClient okHttpClient) {
		return new Picasso.Builder(context)
				.downloader(new OkHttp3Downloader(okHttpClient))
				.loggingEnabled(CleanBaseSettings.imageLoadingLogs())
				.indicatorsEnabled(CleanBaseSettings.imageLoadingLogs())
				.build();
	}

	@Provides @NonNull @Singleton
	public ImageLoader provideImageLoader(@NonNull Picasso picasso) {
		return new PicassoImageLoader(picasso);
	}
}
