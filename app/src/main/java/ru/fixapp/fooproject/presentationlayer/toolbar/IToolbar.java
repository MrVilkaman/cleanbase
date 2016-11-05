package ru.fixapp.fooproject.presentationlayer.toolbar;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public interface IToolbar {


	void hide();

	void show();

	void hideHomeButton();

	void showHomeButton();

	void setTitle(@StringRes int text);

	void setTitle(String text);

	void showIcon(@DrawableRes int Id, Runnable callback);

	void remove(@DrawableRes int resId);
}