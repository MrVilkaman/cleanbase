package ru.fixapp.fooproject.presentationlayer.fragments.core.photocrop;

import android.graphics.Bitmap;

import java.io.File;

import javax.inject.Inject;

import ru.fixapp.fooproject.presentationlayer.fragments.core.BasePresenter;
import ru.fixapp.fooproject.presentationlayer.utils.PhotoUtils;


/**
 * Created by Zahar on 27.03.16.
 */
public class CropImagePresenter extends BasePresenter<CropImageScreenView> {


	@Inject
	public CropImagePresenter() {
	}

	public void savePhoto(){
		Bitmap croppedBitmap = view().getCroppedBitmap();
		if (croppedBitmap == null) {
			return;
		}
		File file = new File(view().getOutPath());
		PhotoUtils.saveToFile(croppedBitmap, file, Bitmap.CompressFormat.JPEG);

		view().sendResults();
	}


}
