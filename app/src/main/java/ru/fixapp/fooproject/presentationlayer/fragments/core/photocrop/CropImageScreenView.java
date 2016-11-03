package ru.fixapp.fooproject.presentationlayer.fragments.core.photocrop;

import android.graphics.Bitmap;

import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseView;


/**
 * Created by Zahar on 27.03.16.
 */
public interface CropImageScreenView extends BaseView {
	Bitmap getCroppedBitmap();

	String getOutPath();

	void sendResults();
}
