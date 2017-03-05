package com.github.mrvilkaman.presentationlayer.resolution;

import android.support.annotation.StringRes;

public interface UIResolver {

	void showToast(@StringRes int textId);

	void showToast(@StringRes int textId, Object... arg);

	void showMessage(@StringRes int textId);

	void showMessage(@StringRes int textId, Object... arg);

	void showMessage(@StringRes int textId, Runnable callback);

	void showSnackbar(@StringRes int textId);

	void showSnackbar(@StringRes int textId, Object... arg);

	void showSnackbar(@StringRes int textId, @StringRes int actionId, Runnable callback);
}
