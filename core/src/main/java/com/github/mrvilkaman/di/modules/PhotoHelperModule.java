package com.github.mrvilkaman.di.modules;

import android.content.Context;

import com.github.mrvilkaman.di.PerScreen;
import com.github.mrvilkaman.presentationlayer.photoulits.PhotoHelper;
import com.github.mrvilkaman.presentationlayer.photoulits.PhotoHelperImpl;
import com.github.mrvilkaman.presentationlayer.utils.StorageUtils;

import dagger.Module;
import dagger.Provides;

@Module
public class PhotoHelperModule {

	@PerScreen
	@Provides
	public PhotoHelper getPhotoHelper(Context context,
									  StorageUtils storageUtils) {
		return new PhotoHelperImpl(context, storageUtils);
	}
}