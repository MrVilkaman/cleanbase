package com.github.mrvilkaman.presentationlayer.fragments.core;


import android.support.annotation.NonNull;

public interface ItemListener<T> {
	void click(@NonNull T value);
}
