package com.github.mrvilkaman.presentationlayer.resolution;

import android.widget.ImageView;

public interface ImageLoader {
	//// TODO: 10.11.16 add more
	void showFromFile(String path, ImageView target);

	void load(String url, int sizeWidth, int sizeHeight, ImageView target);
}
