package com.github.mrvilkaman.namegenerator.datalayer.modules;

import android.support.annotation.NonNull;

import com.github.mrvilkaman.namegenerator.BuildConfig;

import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Zahar on 31.03.16.
 */
@Module
public class NetworkModule {

	@Provides
	@NonNull
	@Singleton
	public OkHttpClient provideOkHttpClient() {
		final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
		Interceptor interceptor = provideHttpLoggingInterceptor();
		if (interceptor != null) {
			okHttpBuilder.addInterceptor(interceptor);
		}

		List<Interceptor> networkInterceptors = provideOkHttpNetworkInterceptors();
		for (Interceptor networkInterceptor : networkInterceptors) {
			okHttpBuilder.addNetworkInterceptor(networkInterceptor);
		}

		return okHttpBuilder.build();
	}

	private Interceptor provideHttpLoggingInterceptor() {
		if (BuildConfig.DEBUG) {
			HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
			httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
			return httpLoggingInterceptor;
		} else {
			return null;
		}
	}


	private List<Interceptor> provideOkHttpNetworkInterceptors() {
		return Collections.EMPTY_LIST;
//		return singletonList(new StethoInterceptor());
	}
}
