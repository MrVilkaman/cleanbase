package com.github.mrvilkaman.presentationlayer.fragments.simplelist;

import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.di.PerScreen;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@PerScreen
@Component(dependencies = ActivityComponent.class,
		modules = {SimpleListScreenComponent.SimpleListScreenModule.class})
public interface SimpleListScreenComponent {

	void inject(SimpleListScreenFragment fragment);


	@Module
	class SimpleListScreenModule {

		@Provides
		@PerScreen
		public SimpleListAdapter getSimpleListAdapter() {
		    return new SimpleListAdapter();
		}
	}
}