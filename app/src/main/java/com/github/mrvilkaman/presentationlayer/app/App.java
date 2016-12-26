package com.github.mrvilkaman.presentationlayer.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.mrvilkaman.BuildConfig;
import com.github.mrvilkaman.di.AppComponent;
import com.github.mrvilkaman.di.DaggerAppComponent;
import com.github.mrvilkaman.di.modules.AppModule;


/**
 * Created by root on 12.03.16.
 */
public class App extends CoreApp<AppComponent> {

	@NonNull
	public static App get(@NonNull Context context) {
		return (App) context.getApplicationContext();
	}

	@Override
	protected void launch(CleanBaseSettings.Builder builder) {
		builder.setSubscribeLogs(BuildConfig.DEBUG);
	}

	@Override
	protected AppComponent createComponent() {
		return DaggerAppComponent.builder()
				.appModule(new AppModule(this))
				.build();
	}

}
