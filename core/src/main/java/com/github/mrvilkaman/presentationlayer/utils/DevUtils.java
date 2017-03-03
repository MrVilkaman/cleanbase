package com.github.mrvilkaman.presentationlayer.utils;


public class DevUtils {

	public static boolean isSnackbarInTheClassPath() {
		try {
			Class.forName("android.support.design.widget.Snackbar");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
