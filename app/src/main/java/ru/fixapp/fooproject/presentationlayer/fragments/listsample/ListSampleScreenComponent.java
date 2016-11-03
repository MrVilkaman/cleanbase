package ru.fixapp.fooproject.presentationlayer.fragments.listsample;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import ru.fixapp.fooproject.di.PerScreen;
import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;

@Component(dependencies = ActivityComponent.class,
		modules = {ListSampleScreenComponent.ListSampleScreenModule.class})
@PerScreen
public interface ListSampleScreenComponent {
	void inject(ListSampleScreenFragment fragment);


	@Module
	class ListSampleScreenModule {
		@Provides
		@PerScreen
		ListSamplePresenter provideListSamplePresenter(SchedulersProvider schedulersProvider) {
			return new ListSamplePresenter(schedulersProvider);
		}
	}
}