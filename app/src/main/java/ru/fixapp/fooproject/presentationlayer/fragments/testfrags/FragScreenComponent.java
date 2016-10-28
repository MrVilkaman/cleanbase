package ru.fixapp.fooproject.presentationlayer.fragments.testfrags;

import dagger.Component;
import dagger.Module;
import ru.fixapp.fooproject.di.PerScreen;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;

@PerScreen
@Component(dependencies = ActivityComponent.class,
		modules = {FragScreenComponent.FragScreenModule.class})
public interface FragScreenComponent {
	void inject(Frag1ScreenFragment fragment);
	void inject(Frag2ScreenFragment fragment);
	void inject(Frag3ScreenFragment fragment);
	void inject(Frag4ScreenFragment fragment);
	void inject(Frag5ScreenFragment fragment);

	@Module
	class FragScreenModule {
	}
}