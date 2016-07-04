package com.github.mrvilkaman.namegenerator.presentationlayer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Zahar on 15.01.2016.
 */
public class UIUtils {


	public static void showToast(Context context, String text) {
		if (context != null)
			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context, int textId) {
		if (context != null)
			Toast.makeText(context, textId, Toast.LENGTH_SHORT).show();
	}

	public static void showAlert(Context context, int resId) {
		if (context == null)
			return;
		showAlert(context, context.getString(resId));
	}

	public static void showAlert(Context context, String resStr) {
		if (context == null)
			return;

		MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
		builder.content(resStr).positiveText(android.R.string.ok).show();
	}

	public static void showAlert(Context context, int text, Runnable callback) {
		if (context == null)
			return;

		showAlert(context, context.getString(text), callback);
	}

	public static void showAlert(Context context, String text, Runnable callback) {
		if (context == null)
			return;

		MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
		builder.content(text).positiveText(android.R.string.ok)
			   .dismissListener(dialog -> callback.run()).show();
	}

	public static String asString(EditText view) {
		if (view == null)
			return null;

		Editable text = view.getText();
		if (text != null)
			return text.toString();
		return null;
	}

	public static void runInUi(Runnable callback) {
		new Handler(Looper.getMainLooper()).post(callback);
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
}
