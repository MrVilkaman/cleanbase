package com.github.mrvilkaman.ui.container;


import android.content.Context;
import android.support.annotation.NonNull;

import com.github.mrvilkaman.BuildConfig;
import com.github.mrvilkaman.di.AppComponent;
import com.github.mrvilkaman.di.DaggerAppComponent;
import com.github.mrvilkaman.di.modules.AppModule;
import com.github.mrvilkaman.presentationlayer.app.CleanBaseSettings;
import com.github.mrvilkaman.presentationlayer.app.CoreApp;

public class App extends CoreApp<AppComponent> {

	@NonNull
	public static App get(@NonNull Context context) {
		return (App) context.getApplicationContext();
	}

	@Override
	protected void launch(CleanBaseSettings.Builder builder) {
		builder.setSubscribeLogs(BuildConfig.DEBUG)
				.setHttpLogging(BuildConfig.DEBUG)
				.setRxBusLogs(BuildConfig.DEBUG)
				.setImageLoadingLogs(BuildConfig.DEBUG);
	}

	@Override
	protected AppComponent createComponent() {
		return DaggerAppComponent.builder()
				.appModule(new AppModule(this))
				.build();
	}

}
