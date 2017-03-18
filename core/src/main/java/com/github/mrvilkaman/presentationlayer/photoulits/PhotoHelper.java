package com.github.mrvilkaman.presentationlayer.photoulits;


import android.content.Intent;

import com.github.mrvilkaman.presentationlayer.fragments.photocrop.CropImageFragment;

@SuppressWarnings("SameParameterValue")
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
