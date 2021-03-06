package com.github.mrvilkaman.presentationlayer.fragments.photomaker;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;

import javax.inject.Inject;

public class PhotoMakerPresenter extends BasePresenter<PhotoMakerView> {

	public static final String KEY_PATH = "path";
	public String imagePath;

	@Inject
	public PhotoMakerPresenter() {
	}

	public void loadFile(String lastPath) {
		if (lastPath != null) {
			this.imagePath = lastPath;
			view().showImage(lastPath);
		}
	}

	public void init(@Nullable Bundle bundle) {
		if (bundle != null) {
			loadFile(bundle.getString(KEY_PATH));
		}
	}

	public void saveInstanceState(@NonNull Bundle bundle) {
		if (imagePath != null) {
			bundle.putString("path", imagePath);
		}
	}
}
