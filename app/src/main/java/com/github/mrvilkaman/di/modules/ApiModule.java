package com.github.mrvilkaman.di.modules;

import android.support.annotation.NonNull;

import com.github.mrvilkaman.BuildConfig;
import com.github.mrvilkaman.datalayer.api.RestApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zahar on 31.03.16.
 */
@Module
public class ApiModule {

	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	@Provides
	@NonNull
	@Singleton
	public Gson provideGson() {
		return new GsonBuilder().setDateFormat(DATE_FORMAT).create();
	}

	@Provides
	@NonNull
	@Singleton
	public RestApi provideApi(@NonNull OkHttpClient okHttpClient, @NonNull Gson gson) {
		return new Retrofit.Builder().baseUrl(RestApi.URL_API).client(okHttpClient)
									 .addConverterFactory(GsonConverterFactory.create(gson))
									 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
									 .validateEagerly(
											 BuildConfig.DEBUG)// Fail early: check Retrofit
									 // configuration at creation time in Debug build.
									 .build().create(RestApi.class);
	}
}
