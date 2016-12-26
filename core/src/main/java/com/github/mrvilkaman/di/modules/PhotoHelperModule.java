package com.github.mrvilkaman.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import com.github.mrvilkaman.di.PerScreen;
import com.github.mrvilkaman.presentationlayer.photoulits.PhotoHelper;
import com.github.mrvilkaman.presentationlayer.photoulits.PhotoHelperImpl;
import com.github.mrvilkaman.presentationlayer.resolution.navigation.NavigationResolver;
import com.github.mrvilkaman.presentationlayer.utils.StorageUtils;

@Module
public class PhotoHelperModule {

	@PerScreen
	@Provides
	public PhotoHelper getPhotoHelper(Context context, NavigationResolver nav,
									  StorageUtils storageUtils) {
		return new PhotoHelperImpl(context, nav, storageUtils);
	}
}