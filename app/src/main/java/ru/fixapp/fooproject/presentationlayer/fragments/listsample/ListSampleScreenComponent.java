package ru.fixapp.fooproject.presentationlayer.fragments.listsample;

import android.support.annotation.NonNull;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import ru.fixapp.fooproject.presentationlayer.app.AppComponent;
import ru.fixapp.fooproject.presentationlayer.app.PerActivity;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ListSampleScreenComponent.ListSampleScreenModule.class})
public interface ListSampleScreenComponent {
	void inject(ListSampleScreenFragment fragment);


	@Module
	class ListSampleScreenModule {
		@Provides
		@NonNull
		ListSamplePresenter provideListSamplePresenter(SchedulersProvider schedulersProvider) {
			return new ListSamplePresenter(schedulersProvider);
		}
	}
}