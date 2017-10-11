package com.github.mrvilkaman.presentationlayer.fragments.photocrop;

import com.github.mrvilkaman.di.PerScreen;

@PerScreen
//@Component()
public interface CropImageScreenComponent {
	void inject(CropImageFragment fragment);
}