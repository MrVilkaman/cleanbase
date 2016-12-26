package com.github.mrvilkaman.presentationlayer.fragments.testfrags;

import dagger.Component;
import dagger.Module;
import com.github.mrvilkaman.di.PerScreen;
import com.github.mrvilkaman.presentationlayer.activities.ActivityCoreComponent;

@PerScreen
@Component(dependencies = ActivityCoreComponent.class,
		modules = {FragScreenComponent.FragScreenModule.class})
public interface FragScreenComponent {
	void inject(Frag1ScreenFragment fragment);
	void inject(Frag2ScreenFragment fragment);
	void inject(Frag3ScreenFragment fragment);
	void inject(Frag4ScreenFragment fragment);
	void inject(Frag5ScreenFragment fragment);

	void inject(DrawerScreenFragment fragment);

	@Module
	class FragScreenModule {
	}
}