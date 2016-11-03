package ru.fixapp.fooproject.presentationlayer.fragments.photomaker;

import dagger.Component;
import dagger.Module;
import ru.fixapp.fooproject.di.PerScreen;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;

@PerScreen
@Component(dependencies = ActivityComponent.class,
		modules = {PhotoMakerScreenComponent.PhotoMakerScreenModule.class})
public interface PhotoMakerScreenComponent {
	void inject(PhotoMakerScreenFragment fragment);


	@Module
	class PhotoMakerScreenModule {

	}
}
