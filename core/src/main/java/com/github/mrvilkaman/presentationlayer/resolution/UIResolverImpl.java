package com.github.mrvilkaman.presentationlayer.resolution;


import android.content.Context;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.presentationlayer.utils.DevUtils;


public class UIResolverImpl implements UIResolver {

	private final Context context;
	private final View rootView;

	// context for show dialogs
	public UIResolverImpl(Context context, View rootView) {
		this.context = context;
		this.rootView = rootView;
	}

	@Override
	public void showToast(@StringRes int textId) {
		Toast.makeText(context, textId, Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void showToast(@StringRes int resId, Object... arg) {
		Toast.makeText(context, context.getString(resId, arg), Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void showMessage(@StringRes int resId) {
		MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
		builder.content(resId)
				.positiveText(android.R.string.ok)
				.show();
	}

	@Override
	public void showMessage(@StringRes int resId, Object... arg) {
		MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
		builder.content(resId, arg)
				.positiveText(android.R.string.ok)
				.show();
	}

	@Override
	public void showMessage(@StringRes int text, Runnable callback) {
		MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
		builder.content(text)
				.positiveText(android.R.string.ok)
				.dismissListener(dialog -> callback.run())
				.show();
	}

	@Override
	public void showSnackbar(@StringRes int textId) {
		if (!DevUtils.isSnackbarInTheClassPath()) {
			return;
		}
		Snackbar snackbar = Snackbar.make(rootView, textId, Snackbar.LENGTH_LONG);
		configSnackbar(snackbar);
	}

	public void configSnackbar(Snackbar snackbar) {
		View snackBarView = snackbar.getView();
		snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.cleanbase_snackbar_bgcolor));
		TextView textView =
				(TextView) snackBarView.findViewById(R.id.snackbar_text);
		textView.setTextColor(ContextCompat.getColor(context, R.color.cleanbase_snackbar_textcolor));
		snackbar.show();
	}

	@Override
	public void showSnackbar(@StringRes int textId, Object... arg) {
		if (!DevUtils.isSnackbarInTheClassPath()) {
			return;
		}
		Snackbar snackbar = Snackbar.make(rootView, context.getString(textId, arg), Snackbar.LENGTH_LONG);
		configSnackbar(snackbar);
	}

	@Override
	public void showSnackbar(@StringRes int textId, @StringRes int actionId, Runnable callback) {
		if (!DevUtils.isSnackbarInTheClassPath()) {
			return;
		}
		Snackbar snackbar = Snackbar.make(rootView, textId, Snackbar.LENGTH_LONG)
				.setActionTextColor(ContextCompat.getColor(context, R.color.cleanbase_snackbar_actioncolor))
				.setAction(actionId, v -> callback.run());
		configSnackbar(snackbar);
	}
}
