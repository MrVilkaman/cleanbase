package ru.fixapp.fooproject.presentationlayer.fragments.core.photocrop;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import ru.fixapp.fooproject.presentationlayer.fragments.core.BasePresenter;


/**
 * Created by Zahar on 27.03.16.
 */
public class CropImagePresenter extends BasePresenter<CropImageScreenView> {


	private static final String TAG = "CropImagePresenter";

	@Inject
	public CropImagePresenter() {
	}

	public void savePhoto(){
		Bitmap croppedBitmap = view().getCroppedBitmap();
		if (croppedBitmap == null) {
			return;
		}
		File file = new File(view().getOutPath());
		saveToFile(croppedBitmap, file, Bitmap.CompressFormat.JPEG);

		view().sendResults();
	}

	private void saveToFile(Bitmap bmp, File filename, Bitmap.CompressFormat format) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filename);
			bmp.compress(format, 90, out); // bmp is your Bitmap instance
		} catch (Exception e) {
			Log.d(TAG, "saveToFile error", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				Log.d(TAG, "saveToFile error", e);
			}
		}
	}


}
