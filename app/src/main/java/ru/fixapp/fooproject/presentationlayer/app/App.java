package ru.fixapp.fooproject.presentationlayer.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import ru.fixapp.fooproject.di.AppComponent;
import ru.fixapp.fooproject.di.DaggerAppComponent;
import ru.fixapp.fooproject.di.modules.AppModule;


/**
 * Created by root on 12.03.16.
 */
public class App extends Application {

	@NonNull private AppComponent appComponent;

	// Prevent need in a singleton (global) reference to the application object.
	@NonNull
	public static App get(@NonNull Context context) {
		return (App) context.getApplicationContext();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		appComponent = prepareApplicationComponent().build();
	}

	protected DaggerAppComponent.Builder prepareApplicationComponent() {
		return DaggerAppComponent.builder()
				.appModule(new AppModule(this));
	}

	@NonNull
	public AppComponent getAppComponent() {
		return appComponent;
	}
}
