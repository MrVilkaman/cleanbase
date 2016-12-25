package ru.fixapp.fooproject.presentationlayer.utils;


import android.content.Context;
import android.os.Environment;

import java.io.File;


public class StorageUtils {

	public String getStoragePath(Context context) {
		if (context == null)
			return null;

		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File externalDir = context.getExternalFilesDir(null);
			if (externalDir != null)
				return externalDir.toString() + File.separator;
		}

		File filesDir = context.getFilesDir();
		return filesDir != null ? filesDir.getAbsolutePath() + File.separator : null;
	}
}
