package ru.fixapp.fooproject.presentationlayer.activities;


import android.content.Context;
import android.view.View;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import ru.fixapp.fooproject.di.AppComponent;
import ru.fixapp.fooproject.di.PerActivity;
import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import ru.fixapp.fooproject.presentationlayer.resolution.NavigationResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.NavigationResolverImpl;
import ru.fixapp.fooproject.presentationlayer.resolution.ThrowableResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.ThrowableResolverImpl;
import ru.fixapp.fooproject.presentationlayer.resolution.UIResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.UIResolverImpl;

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityComponent.ActivityModule.class)
public interface ActivityComponent {
	void inject(MainActivity activity);

	SchedulersProvider getSchedulersProvider();

	UIResolver getUIResolver();

	ThrowableResolver getThrowableResolver();

	NavigationResolver getNavigationResolver();

	@Module
	public class ActivityModule {

		private View view;

		public ActivityModule(View view) {
			this.view = view;
		}

		@Provides
		@PerActivity
		public UIResolver createUiResolver(Context context) {
			return new UIResolverImpl(context,view);
		}

		@Provides
		@PerActivity
		public ThrowableResolver createThrowableResolver(UIResolver ui) {
			return new ThrowableResolverImpl(ui);
		}

		@Provides
		@PerActivity
		public NavigationResolver createNavigationResolver() {
			return new NavigationResolverImpl();
		}

	}
}