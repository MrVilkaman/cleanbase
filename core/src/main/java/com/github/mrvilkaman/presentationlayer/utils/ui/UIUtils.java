package com.github.mrvilkaman.presentationlayer.utils.ui;

import android.os.Build;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;


/**
 * Created by Zahar on 15.01.2016.
 */
public class UIUtils {

	private UIUtils() {
		// пустой
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

	@SuppressWarnings("deprecation")
	public static Spanned fromHtml(String source) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
		} else {
			return Html.fromHtml(source);
		}
	}

//	public static void openSettings(NavigationResolver navigation,String packageName) {
//		final Intent i = new Intent();
//		i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//		i.addCategory(Intent.CATEGORY_DEFAULT);
//		i.setData(Uri.parse("package:" + packageName));
//		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//		i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//		navigation.startActivityForResult(i, -1);
//	}
}
