package ru.fixapp.fooproject.presentationlayer.utils;

import android.support.annotation.DrawableRes;

public interface IToolbar {

    void hide();

    void show();

    void setText(int text);

    void setText(String text);

    void hideHomeButton();

    void showHomeButton();

    void showIcon(@DrawableRes int Id, Runnable callback);

    void remove(@DrawableRes int resId);
}