package ru.fixapp.fooproject.presentationlayer.photoulits;


import android.content.Intent;

import ru.fixapp.fooproject.presentationlayer.fragments.core.photocrop.CropImageFragment;

public interface PhotoHelper {

	interface PhotoHelperCallback{
		void onGetPath(String path);
	}

	void onActivityResult(int requestCode, int resultCode, Intent data, PhotoHelperCallback callback);

	void openCamera(CropImageFragment.MODE free);

	void openCamera(CropImageFragment.MODE free, String fileName);

	void openGallery(CropImageFragment.MODE free);

	void openGallery(CropImageFragment.MODE free, String fileName);
}
