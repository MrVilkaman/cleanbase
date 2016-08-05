package ru.fixapp.fooproject.presentationlayer.utils;

import android.view.View;

public interface IToolbar {

    void hide();

    void setText(int text);

    void setText(String text);

    void addView(int layoutId, OnToolbarListener viewCreated);

    void clear();

    void hideHomeIcon();

    void showHomeIcon();

    void showBackIcon();


    interface OnToolbarListener {
        void onToolbarViewCreated(View view);
    }

    interface OnHomeClick {
        void onClick();
    }

}
