package com.github.mrvilkaman.presentationlayer.utils;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Zahar on 15.01.2016.
 */
public class UIUtils {

	private UIUtils(){}

	public static String asString(EditText view) {
		if (view == null)
			return "";

		Editable text = view.getText();
		if (text != null)
			return text.toString();
		return "";
	}

	public static void changeVisibility(View view, boolean show) {
		view.setVisibility(show ? View.VISIBLE : View.GONE);
	}
}
