package ru.fixapp.fooproject.presentationlayer.photoulits;


import android.content.Intent;

import ru.fixapp.fooproject.presentationlayer.fragments.core.photocrop.CropImageFragment;

public interface PhotoHelper {
	boolean onActivityResult(int requestCode, int resultCode, Intent data);

	void openCamera(CropImageFragment.MODE free);

	void openCamera(CropImageFragment.MODE free, String fileName);

	void openGallery(CropImageFragment.MODE free);

	void openGallery(CropImageFragment.MODE free, String fileName);

	String getLastPath();
}
