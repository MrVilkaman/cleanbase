package ru.fixapp.fooproject.presentationlayer.fragments.photomaker;

import android.content.Context;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import ru.fixapp.fooproject.di.PerScreen;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;
import ru.fixapp.fooproject.presentationlayer.photoulits.PhotoHelper;
import ru.fixapp.fooproject.presentationlayer.photoulits.PhotoHelperImpl;
import ru.fixapp.fooproject.presentationlayer.resolution.NavigationResolver;

@PerScreen
@Component(dependencies = ActivityComponent.class,
		modules = {PhotoMakerScreenComponent.PhotoMakerScreenModule.class})
public interface PhotoMakerScreenComponent {
	void inject(PhotoMakerScreenFragment fragment);


	@Module
	class PhotoMakerScreenModule {

		@PerScreen
		@Provides
		public PhotoHelper getPhotoHelper(Context context, NavigationResolver nav) {
			return new PhotoHelperImpl(context, nav);
		}
	}
}
