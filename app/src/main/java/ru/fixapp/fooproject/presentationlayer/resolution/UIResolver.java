package ru.fixapp.fooproject.presentationlayer.resolution;

import android.support.annotation.StringRes;

public interface UIResolver {

	void showToast(@StringRes int textId);

	void showMessage(@StringRes int resId);

	void showMessage(@StringRes int resId, Object... arg);

	void showMessage(@StringRes int text, Runnable callback);

	void showSnackbar(@StringRes int textId);
}
