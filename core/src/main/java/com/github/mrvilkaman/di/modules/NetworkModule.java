package com.github.mrvilkaman.di.modules;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.mrvilkaman.presentationlayer.app.CleanBaseSettings;

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


	private final List<Interceptor> networkInterceptors;
	@Nullable private final BuilderProcessor processor;

	public NetworkModule() {
		this(Collections.<Interceptor>emptyList(), null);

	}

	public NetworkModule(List<Interceptor> networkInterceptors,
						 @Nullable BuilderProcessor processor) {
		this.networkInterceptors = networkInterceptors;
		this.processor = processor;
	}

	public NetworkModule(List<Interceptor> networkInterceptors) {
		this(networkInterceptors, null);
	}

	public NetworkModule(BuilderProcessor processor) {
		this(Collections.<Interceptor>emptyList(), processor);
	}

	@Provides
	@NonNull
	@Singleton
	public OkHttpClient provideOkHttpClient() {
		final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

		if (processor != null) {
			processor.handle(okHttpBuilder);
		}

		Interceptor interceptor = provideHttpLoggingInterceptor();
		if (interceptor != null) {
			okHttpBuilder.addInterceptor(interceptor);
		}

		for (Interceptor networkInterceptor : networkInterceptors) {
			okHttpBuilder.addNetworkInterceptor(networkInterceptor);
		}

		return okHttpBuilder.build();
	}

	private Interceptor provideHttpLoggingInterceptor() {
		if (CleanBaseSettings.httpLogging()) {
			HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
			httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
			return httpLoggingInterceptor;
		} else {
			return null;
		}
	}

	public interface BuilderProcessor {
		void handle(OkHttpClient.Builder builder);
	}

}
