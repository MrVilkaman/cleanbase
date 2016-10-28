package ru.fixapp.fooproject.presentationlayer.activities;

import android.support.v4.app.FragmentActivity;

public interface BaseActivityPresenter {

	void openActivity(Class<? extends FragmentActivity> aClass);
}
