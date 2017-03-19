package com.github.mrvilkaman.presentationlayer.utils.ui;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.github.mrvilkaman.presentationlayer.resolution.navigation.NavigationResolver;

/**
 * Created by Zahar on 15.01.2016.
 */
public class UIUtils {

	private UIUtils() {
	}

	public static String asString(EditText view) {
		if (view == null)
			return "";

		Editable text = view.getText();
		if (text != null)
			return text.toString();
		return "";
	}

	public static void changeVisibility(View view, boolean show) {
		if (view != null)
			view.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	public static void openSettings(NavigationResolver navigation,String packageName) {
		final Intent i = new Intent();
		i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		i.addCategory(Intent.CATEGORY_DEFAULT);
		i.setData(Uri.parse("package:" + packageName));
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		navigation.startActivityForResult(i, -1);
	}
}
