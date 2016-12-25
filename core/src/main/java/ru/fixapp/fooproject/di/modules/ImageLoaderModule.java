package ru.fixapp.fooproject.di.modules;


import android.content.Context;
import android.support.annotation.NonNull;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import ru.fixapp.fooproject.presentationlayer.resolution.ImageLoader;
import ru.fixapp.fooproject.presentationlayer.resolution.PicassoImageLoader;

@Module
@Singleton
public class ImageLoaderModule {

	@Provides
	@NonNull
	@Singleton
	public Picasso providePicasso(@NonNull Context context, @NonNull OkHttpClient okHttpClient) {
		return new Picasso.Builder(context)
				.downloader(new OkHttp3Downloader(okHttpClient))
				.build();
	}

	@Provides @NonNull @Singleton
	public ImageLoader provideImageLoader(@NonNull Picasso picasso) {
		return new PicassoImageLoader(picasso);
	}
}
