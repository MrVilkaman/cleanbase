package ru.fixapp.fooproject.presentationlayer.fragments.testerror;

import dagger.Component;
import dagger.Module;
import ru.fixapp.fooproject.di.PerScreen;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;

@PerScreen
@Component(dependencies = ActivityComponent.class,
		modules = {TestErrorScreenComponent.TestErrorScreenModule.class})
public interface TestErrorScreenComponent {
	void inject(TestErrorScreenFragment fragment);


	@Module
	class TestErrorScreenModule {

	}
}