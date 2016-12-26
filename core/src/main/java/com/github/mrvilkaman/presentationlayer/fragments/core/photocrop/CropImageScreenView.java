package com.github.mrvilkaman.presentationlayer.fragments.core.photocrop;

import android.graphics.Bitmap;

import com.github.mrvilkaman.presentationlayer.fragments.core.BaseView;


/**
 * Created by Zahar on 27.03.16.
 */
public interface CropImageScreenView extends BaseView {
	Bitmap getCroppedBitmap();

	String getOutPath();

	void sendResults();
}
