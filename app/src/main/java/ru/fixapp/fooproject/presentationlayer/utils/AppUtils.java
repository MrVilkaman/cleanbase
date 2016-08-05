package ru.fixapp.fooproject.presentationlayer.utils;

import android.content.res.Resources;
import android.os.Build;

/**
 * Created by Zahar on 22.01.2016.
 */
public class AppUtils {
	private AppUtils() {
	}

	public static int dpToPixel(float dp) {
		return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}

	public static float pixelsToDp(float px) {
		return (int) (px / Resources.getSystem().getDisplayMetrics().density);
	}

	public static boolean isSamsung() {
		return Build.MANUFACTURER.compareToIgnoreCase("samsung") != 0;
	}
}
