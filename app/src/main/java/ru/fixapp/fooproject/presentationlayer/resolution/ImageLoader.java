package ru.fixapp.fooproject.presentationlayer.resolution;

import android.widget.ImageView;

public interface ImageLoader {
	void showFromFile(String path, ImageView target);
}
