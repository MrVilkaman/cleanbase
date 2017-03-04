package com.github.mrvilkaman.presentationlayer.utils;

/**
 * Created by Zahar on 22.01.2016.
 */
@Deprecated
public class AppUtils {
	private AppUtils() {
	}


	@Deprecated
	public static int dpToPixel(float dp) {
		return DevUtils.dpToPixel(dp);
	}

	@Deprecated
	public static float pixelsToDp(float px) {
		return DevUtils.pixelsToDp(px);
	}

	@Deprecated
	public static boolean isSamsung() {
		return DevUtils.isSamsung();
	}
	}
