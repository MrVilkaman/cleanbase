package ru.fixapp.fooproject.presentationlayer.toolbar;

import android.support.annotation.DrawableRes;
import android.view.View;

public interface IToolbar {

    void init(View view);

    void hide();

    void show();

    void setText(int text);

    void setText(String text);

    void hideHomeButton();

    void showHomeButton();

    void showIcon(@DrawableRes int Id, Runnable callback);

    void remove(@DrawableRes int resId);
}