package com.github.mrvilkaman.presentationlayer.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.github.mrvilkaman.di.AppCoreComponent;
import com.github.mrvilkaman.di.IHasComponent;

public abstract class CoreApp<C extends AppCoreComponent> extends Application
		implements IHasComponent<C> {

	@NonNull protected C appComponent;


	@NonNull
	public static CoreApp get(@NonNull Context context) {
		return (CoreApp) context.getApplicationContext();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		appComponent = createComponent();
	}

	protected abstract C createComponent();

	@Override
	public C getComponent() {
		return appComponent;
	}
}
