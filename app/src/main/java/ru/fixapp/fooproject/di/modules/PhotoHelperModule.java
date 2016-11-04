package ru.fixapp.fooproject.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.fixapp.fooproject.di.PerScreen;
import ru.fixapp.fooproject.presentationlayer.photoulits.PhotoHelper;
import ru.fixapp.fooproject.presentationlayer.photoulits.PhotoHelperImpl;
import ru.fixapp.fooproject.presentationlayer.resolution.NavigationResolver;

@Module
public class PhotoHelperModule {

	@PerScreen
	@Provides
	public PhotoHelper getPhotoHelper(Context context, NavigationResolver nav) {
		return new PhotoHelperImpl(context, nav);
	}
}