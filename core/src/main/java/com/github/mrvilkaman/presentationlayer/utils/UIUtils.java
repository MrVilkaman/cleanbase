package com.github.mrvilkaman.presentationlayer.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Zahar on 15.01.2016.
 */
public class UIUtils {

	public static String asString(EditText view) {
		if (view == null)
			return null;

		Editable text = view.getText();
		if (text != null)
			return text.toString();
		return null;
	}

	public static void openLink(Activity activity, String link) {
		final String url;
		if (!link.startsWith("http://") && !link.startsWith("https://"))
			url = "http://" + link;
		else
			url = link;

		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		activity.startActivity(browserIntent);
	}


	public static void changeVisibility(View view, boolean show) {
		view.setVisibility(show ? View.VISIBLE : View.GONE);
	}
}
