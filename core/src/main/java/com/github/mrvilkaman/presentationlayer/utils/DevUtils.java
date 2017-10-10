package com.github.mrvilkaman.presentationlayer.utils;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.StrictMode;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.github.mrvilkaman.di.IHasComponent;

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
		return (int) (dp * Resources.getSystem()
				.getDisplayMetrics().density);
	}

	public static float pixelsToDp(float px) {
		return (int) (px / Resources.getSystem()
				.getDisplayMetrics().density);
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
		return String.format("\n%s\n%s\n%s", stackTrace[i].toString(), stackTrace[i + 1]
						.toString(),
				stackTrace[i + 2].toString());

	}

	@SuppressWarnings("unchecked")
	public static <T> T getComponent(Object obj, Class<T> componentType) {
		return componentType.cast(((IHasComponent<T>) obj).getComponent());
	}

	public static void useStrictMode() {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll()
				.penaltyLog()
				.penaltyDialog()
				.build());
		StrictMode.setVmPolicy(
				new StrictMode.VmPolicy.Builder().detectAll()
						.penaltyLog()
						.penaltyDeath()
						.build());
	}


	public static void hideKeyboard(Activity activity) {
		View view = activity.getCurrentFocus();
		if (view != null) {
			view.clearFocus();
			InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context
					.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
}
