package ru.fixapp.fooproject.presentationlayer.fragments.photomaker;

import dagger.Component;
import ru.fixapp.fooproject.di.PerScreen;
import ru.fixapp.fooproject.di.modules.PhotoHelperModule;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityCoreComponent;

@PerScreen
@Component(dependencies = ActivityCoreComponent.class,
		modules = {PhotoHelperModule.class})
public interface PhotoMakerScreenComponent {
	void inject(PhotoMakerScreenFragment fragment);

}
