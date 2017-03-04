package com.github.mrvilkaman.presentationlayer.utils;


import android.content.res.Resources;
import android.os.Build;

public class DevUtils {

	public static boolean isSnackbarInTheClassPath() {
		try {
			Class.forName("android.support.design.widget.Snackbar");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
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

	public static String getSubscriberStartStack() {
		StackTraceElement[] stackTrace = new Exception().getStackTrace();

		boolean findInit = false;
		for (int i = 1; i < stackTrace.length; i++) {
			String methodName = stackTrace[i].getMethodName();
			if (methodName.equals("<init>")) {
				findInit = true;
			} else {
				if (findInit) {
					return String.format("\n%s\n%s", stackTrace[i].toString(),
							stackTrace[i + 1].toString());
				}
			}
		}
		return "";
	}

	public static String getGlobalSubscriberStartStack() {
		StackTraceElement[] stackTrace = new Exception().getStackTrace();
		int i = 2;
		return String.format("\n%s\n%s\n%s", stackTrace[i].toString(), stackTrace[i + 1].toString(),
				stackTrace[i + 2].toString());

	}
}
